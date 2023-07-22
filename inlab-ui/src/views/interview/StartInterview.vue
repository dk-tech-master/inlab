<template>
  <section class="flex items-center justify-center py-24">
    <div class="w-1/4">
      <div class="flex items-center">
        <a href="#" class="flex w-1/2 items-center mx-auto mb-10">
          <img class="w-full" src="../../images/logo-color.svg" alt="logo" />
        </a>
      </div>
      <div>
        <p class="mb-7 text-3xl font-bold text-gray-800 text-center">
          면접을 시작합니다
        </p>
        <p class="mb-12 text-2xl text-center text-gray-800">
          오늘도 훌륭한 인재를 선발해봅시다😀
        </p>
      </div>
      <div class="rounded-lg border p-12 shadow">
        <div>
          <div class="mb-4">
            <label
              for="intervieweeName"
              class="block mb-2 text-sm font-semibold text-gray-900"
              >면접자 이름</label
            >
            <input
              v-model="interviewee"
              type="text"
              name="intervieweeName"
              placeholder="면접자 이름"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
            />
          </div>
          <div class="mb-8">
            <label
              for="intervieweeNumber"
              class="block mb-2 text-sm font-semibold text-gray-900"
              >면접자 번호</label
            >
            <input
              v-model="intervieweeNo"
              type="text"
              name="intervieweeName"
              placeholder="......"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
            />
          </div>
          <button
            type="button"
            class="btn btn-primary w-full"
            @click="clickStartInterviewBtn"
          >
            면접 시작
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";

const route = useRoute();
const router = useRouter();
const interviewee = ref();
const intervieweeNo = ref();

const clickStartInterviewBtn = async () => {
  await router.push(
    `/interview/process/${route.params.interviewId}?interviewee=${interviewee.value}&intervieweeNo=${intervieweeNo.value}`,
  );
};
</script>