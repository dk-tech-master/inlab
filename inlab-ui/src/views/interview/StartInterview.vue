<template>
  <section
    class="flex flex-col px-7 py-8 items-center justify-center md:h-screen lg:py-0"
  >
    <div class="w-full rounded-lg md:mt-0 sm:max-w-md xl:p-0">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        class="w-10 h-10 text-primary mx-auto mb-3"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 002.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 00-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 00.75-.75 2.25 2.25 0 00-.1-.664m-5.8 0A2.251 2.251 0 0113.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25zM6.75 12h.008v.008H6.75V12zm0 3h.008v.008H6.75V15zm0 3h.008v.008H6.75V18z"
        />
      </svg>

      <h3
        class="mb-7 text-xl text-center font-semibold tracking-tighter text-gray-800"
      >
        면접 시작
      </h3>
      <p
        class="mb-12 text-lg text-center tracking-tighter font-medium text-gray-700"
      >
        훌륭한 인재를 선발해봅시다.<br />
        오늘도 면접 파이팅입니다.<br />
      </p>
      <div class="px-5">
        <div>
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
        <div class="mt-5">
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
          class="btn btn-primary w-full mt-12"
          @click="clickStartInterviewBtn"
        >
          면접 시작
        </button>
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
