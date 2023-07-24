<template>
  <section class="flex justify-center items-center py-40">
    <div>
      <div class="flex justify-center mb-14">
        <img src="./../../images/logo-color.svg" />
      </div>
      <div class="flex justify-center mb-8">
        <h1 class="font-semibold text-4xl">해당 페이지를 찾지 못했습니다.</h1>
      </div>
      <div class="text-2xl">
        <p class="text-center mb-4">
          주소가 잘못되었거나 더 이상 제공되지 않는 페이지입니다.
        </p>
        <p
          class="text-center text-primary cursor-pointer"
          @click="clickHomeBtn"
        >
          메인페이지로 이동 🏠🏃🏻‍♀
        </p>
      </div>
    </div>
  </section>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const clickHomeBtn = () => {
  router.push("/question");
};
</script>

<style scoped></style>