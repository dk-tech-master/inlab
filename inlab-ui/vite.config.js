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
          "https://clovaspeech-gw.ncloud.com/external/v1/5406/2d009610689a0476b0a5736b5d7f35a70a544d7feef6e8f81de2271b2794300a",
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
