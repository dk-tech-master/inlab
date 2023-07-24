package kr.inlab.www.service;

import com.jcraft.jsch.*;
import kr.inlab.www.common.exception.BlobFileTransferException;
import kr.inlab.www.common.exception.FileConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${audio.upload-path}")
    private String audioUploadPath;
    @Value("${audio.root-url}")
    private String audioRootUrl;
    @Value("${audio.extension}")
    private String audioExtension;
    @Value("${sftp.host}")
    private String host;
    @Value("${sftp.port}")
    private Integer port;
    @Value("${sftp.username}")
    private String username;
    @Value("${sftp.password}")
    private String password;
    @Value("${sftp.sessionTimeout}")
    private Integer sessionTimeout;
    @Value("${sftp.channelTimeout}")
    private Integer channelTimeout;
    @Value("${sftp.remote-file-directory}")
    private String remoteFileDirectory;

    @Override
    public File saveBlobFile(MultipartFile blob) {
        try {
            File tempFile = File.createTempFile(audioUploadPath + "/temp", null);
            blob.transferTo(tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new BlobFileTransferException();
        }
    }

    @Override
    public File convertBlobToMp3(File blob) {
        try {
            // FFmpeg을 사용하여 Blob 파일을 MP3로 변환
            String fileName = UUID.randomUUID().toString();
            String outputFilePath = audioUploadPath + "/" + fileName + audioExtension;

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ffmpeg",
                    "-i",
                    blob.getAbsolutePath(),
                    "-acodec",
                    "libmp3lame",
                    outputFilePath
            );
            Process process = processBuilder.start();
            process.waitFor();

            return new File(outputFilePath);
        } catch (IOException | InterruptedException e) {
            throw new FileConvertException();
        }
    }

    @Override
    public String transferToFileServer(File mp3) {
        ChannelSftp channelSftp = createChannelSftp();

        try {
            channelSftp.put(mp3.getAbsolutePath(), remoteFileDirectory + "/" + mp3.getName());
            return audioRootUrl + "/" + mp3.getName();
        } catch (SftpException e) {
            log.error("Error upload file", e);
        } finally {
            disconnectChannelSftp(channelSftp);
        }

        return null;
    }

    private ChannelSftp createChannelSftp() {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect(sessionTimeout);
            Channel channel = session.openChannel("sftp");
            channel.connect(channelTimeout);
            return (ChannelSftp) channel;
        } catch (JSchException e) {
            log.error("Create ChannelSftp error", e);
        }

        return null;
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if (channelSftp == null)
                return;

            if (channelSftp.isConnected())
                channelSftp.disconnect();

            if (channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch (Exception e) {
            log.error("SFTP disconnect error", e);
        }
    }
}
