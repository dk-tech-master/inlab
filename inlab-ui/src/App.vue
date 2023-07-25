<template>
  <main class="flex">
    <SideBar v-if="$route.meta.hide" />
    <div class="w-full">
      <Header v-if="$route.meta.hide" />
      <div class="py-16 px-20">
        <RouterView />
      </div>
    </div>
  </main>
</template>

<script setup>
import { RouterView } from "vue-router";
import SideBar from "@/components/common/SideBar.vue";
import Header from "@/components/common/Header.vue";

if (import.meta.env.PROD) {
  console.log = () => {};
}
</script>
