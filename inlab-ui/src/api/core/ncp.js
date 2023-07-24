import axios from "axios";

const ncp = axios.create({
  timeout: 30000,
  headers: {
    "X-CLOVASPEECH-API-KEY": import.meta.env.VITE_NCP_KEY,
  },
});

export default ncp;
