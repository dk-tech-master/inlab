<template>
  <Header v-if="show" />
  <SideBar v-if="show" />
  <RouterView />
</template>

<script setup>
import { RouterView } from "vue-router";
import { ref } from "vue";
import SideBar from "@/components/common/SideBar.vue";
import Header from "@/components/common/Header.vue";

const show = ref("false");
</script>
