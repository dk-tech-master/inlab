<template>
  <section class="ml-80">
    <button id="record-button" class="btn" @click="startRecording">녹음</button>
    <button id="stop-button" class="btn" @click="stopRecording">정지</button>
    <audio ref="audioPlayer" controls></audio>
  </section>
</template>
<script setup>
import { ref } from "vue";
import { uploadAudioFile } from "@/api/file";
import { speechToText } from "@/api/clova";

const audioPlayer = ref(null);
const recording = ref(false);
const mediaRecorder = ref(null);
const chunks = ref([]);
const audioFileUrl = ref("");

const audioToText = async (blob) => {
  const formData = new FormData();
  formData.append("blob", blob);

  const uploadAudioFileResult = await uploadAudioFile(formData);
  audioFileUrl.value = uploadAudioFileResult.data.audioFileUrl;

  const data = {
    url: audioFileUrl.value,
    language: "ko-KR",
    completion: "sync",
  };
  const speechToTextResult = await speechToText(data);
  console.log(speechToTextResult.data);
};

const startRecording = () => {
  chunks.value = [];

  navigator.mediaDevices
    .getUserMedia({ audio: true })
    .then((stream) => {
      recording.value = true;
      mediaRecorder.value = new MediaRecorder(stream);

      mediaRecorder.value.addEventListener("dataavailable", (event) => {
        chunks.value.push(event.data);
      });

      mediaRecorder.value.addEventListener("stop", () => {
        const blob = new Blob(chunks.value, { type: "audio/mp3" });
        const url = URL.createObjectURL(blob);
        audioPlayer.value.src = url;

        audioToText(blob);
      });

      mediaRecorder.value.start();
    })
    .catch((error) => {
      console.error(error);
    });
};

const stopRecording = () => {
  recording.value = false;
  mediaRecorder.value.stop();
};
</script>