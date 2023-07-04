<template>
  <SideBar />
  <RouterView />
</template>

<script setup>
import { RouterView } from "vue-router";
import SideBar from "@/components/common/SideBar.vue";
</script>
