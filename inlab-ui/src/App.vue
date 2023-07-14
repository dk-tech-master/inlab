<template>
  <main class="flex">
    <SideBar v-if="$route.meta.hide" />
    <div class="w-full">
      <Header v-if="$route.meta.hide" />
      <div class="ml-12">
        <RouterView />
      </div>
    </div>
  </main>
</template>

<script setup>
import { RouterView } from "vue-router";
import { ref } from "vue";
import SideBar from "@/components/common/SideBar.vue";
import Header from "@/components/common/Header.vue";

const show = ref("false");
</script>
