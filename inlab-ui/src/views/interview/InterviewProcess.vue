<template>
  <header class="w-3/4 mx-auto mt-8">
    <div>
      <h4 class="text-xl mb-5">스프링 개발자 면접</h4>
    </div>
  </header>
  <section class="w-3/4 mx-auto">
    <div class="flex justify-between border-b-2">
      <span class="text-base font-semibold text-gray-800">질문01</span>
      <div class="form-control">
        <label class="label cursor-pointer">
          <span class="label-text mr-3">{{ toggleLabel }}</span>
          <input v-model="isRecording" type="checkbox" class="toggle" checked />
        </label>
      </div>
    </div>
    <h3 class="text-gray-800 my-5 font-semibold">
      스프링 프레임워크의 핵심 개념과 이점에 대해 설명해주세요.
    </h3>
    <div
      v-for="index in 4"
      :key="index"
      class="flex items-center justify-start mb-3"
    >
      <input
        type="checkbox"
        checked="checked"
        class="rounded focus:ring-2 focus:ring-primary checkbox checkbox-sm checkbox-primary"
      />
      <p for="default-checkbox" class="ml-2 text-sm font-medium text-gray-900">
        해당 질문을 제대로 대답했는가?
      </p>
    </div>
    <div class="my-8">
      <label class="label">
        <span class="label-text">코멘트</span>
      </label>
      <textarea
        class="w-full bg-gray-100 textarea textarea-bordered h-24"
        placeholder="면접관님의 의견을 적어주세요"
      ></textarea>
    </div>
    <div class="my-8">
      <label class="label">
        <span class="label-text">응답기록</span>
      </label>
      <textarea
        class="w-full bg-gray-100 textarea textarea-bordered h-24"
      ></textarea>
    </div>
  </section>

  <!--  footer고정-->
  <div
    class="fixed bottom-0 left-0 z-50 w-full h-16 bg-white border-t border-gray-200"
  >
    <div class="flex justify-center h-full max-w-4xl mx-auto font-medium">
      <button
        type="button"
        class="inline-flex flex-col items-center justify-center px-12 hover:bg-gray-50 group"
      >
        <div class="flex flex-col items-center">
          <div class="stat-title text-xs">오늘의 면접자</div>
          <h2 class="text-primary text-xl">신다인</h2>
        </div>
      </button>
      <button
        type="button"
        class="inline-flex flex-col items-center justify-center px-12 hover:bg-gray-50 group"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          class="w-5 h-5 mb-2 text-gray-500 dark:text-gray-400 group-hover:text-indigo-600 dark:group-hover:text-indigo-600"
        >
          <path
            fill-rule="evenodd"
            d="M4.125 3C3.089 3 2.25 3.84 2.25 4.875V18a3 3 0 003 3h15a3 3 0 01-3-3V4.875C17.25 3.839 16.41 3 15.375 3H4.125zM12 9.75a.75.75 0 000 1.5h1.5a.75.75 0 000-1.5H12zm-.75-2.25a.75.75 0 01.75-.75h1.5a.75.75 0 010 1.5H12a.75.75 0 01-.75-.75zM6 12.75a.75.75 0 000 1.5h7.5a.75.75 0 000-1.5H6zm-.75 3.75a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5H6a.75.75 0 01-.75-.75zM6 6.75a.75.75 0 00-.75.75v3c0 .414.336.75.75.75h3a.75.75 0 00.75-.75v-3A.75.75 0 009 6.75H6z"
            clip-rule="evenodd"
          />
          <path
            d="M18.75 6.75h1.875c.621 0 1.125.504 1.125 1.125V18a1.5 1.5 0 01-3 0V6.75z"
          />
        </svg>

        <span
          class="text-sm text-gray-500 group-hover:text-indigo-600 dark:group-hover:text-indigo-500"
          >면접 시작</span
        >
      </button>
      <button
        type="button"
        class="inline-flex flex-col items-center justify-center px-12 hover:bg-gray-50 dark:hover:bg-gray-800 group"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          class="w-5 h-5 mb-2 text-gray-500 dark:text-gray-400 group-hover:text-indigo-600 dark:group-hover:text-indigo-600"
        >
          <path
            fill-rule="evenodd"
            d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12zM12 8.25a.75.75 0 01.75.75v3.75a.75.75 0 01-1.5 0V9a.75.75 0 01.75-.75zm0 8.25a.75.75 0 100-1.5.75.75 0 000 1.5z"
            clip-rule="evenodd"
          />
        </svg>

        <span
          class="text-sm text-gray-500 group-hover:text-indigo-600 dark:group-hover:text-indigo-500"
          >면접 취소</span
        >
      </button>
      <button
        type="button"
        class="inline-flex flex-col items-center justify-center px-12 hover:bg-gray-50 dark:hover:bg-gray-800 group"
      >
        <svg
          class="w-5 h-5 mb-2 text-gray-500 dark:text-gray-400 group-hover:text-indigo-600 dark:group-hover:text-blue-500"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="currentColor"
          viewBox="0 0 20 20"
        >
          <path
            d="m19.707 9.293-2-2-7-7a1 1 0 0 0-1.414 0l-7 7-2 2a1 1 0 0 0 1.414 1.414L2 10.414V18a2 2 0 0 0 2 2h3a1 1 0 0 0 1-1v-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v4a1 1 0 0 0 1 1h3a2 2 0 0 0 2-2v-7.586l.293.293a1 1 0 0 0 1.414-1.414Z"
          />
        </svg>
        <span
          class="text-sm text-gray-500 group-hover:text-indigo-600 dark:group-hover:text-indigo-500"
          >면접 종료
        </span>
      </button>
    </div>
  </div>
</template>
<script setup>
import { computed, ref } from "vue";
import Header from "@/App.vue";

const isRecording = ref(true);

const toggleLabel = computed(() => {
  return isRecording.value ? "녹음 중" : "녹음 시작";
});
</script>
