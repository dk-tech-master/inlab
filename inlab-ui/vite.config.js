import { fileURLToPath, URL } from "node:url";

// eslint-disable-next-line import/no-extraneous-dependencies
import { defineConfig } from "vite";
// eslint-disable-next-line import/no-extraneous-dependencies
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    proxy: {
      "/ncp": {
        target:
          "https://clovaspeech-gw.ncloud.com/external/v1/5597/747ce67261a19415ee7cea70a3e0f11f3b007e98629a3b9d5fde50ee6caa514e",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/ncp/, ""),
      },
      "/inlab": {
        target: "http://localhost:8080",
        rewrite: (path) => path.replace(/^\/inlab/, ""),
      },
    },
  },
});
