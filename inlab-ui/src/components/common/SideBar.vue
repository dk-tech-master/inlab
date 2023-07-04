<template>
  <aside
    id="sidebar-multi-level-sidebar"
    class="fixed top-0 left-0 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0"
    aria-label="Sidebar"
  >
    <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50">
      <ul class="space-y-2 font-medium">
        <div class="w-3/5 pt-5 pb-10 px-2">
          <img src="../../images/logo-color.svg" alt="logo" />
        </div>
        <!--        1. 면접관 관리-->
        <li>
          <a
            href="#"
            class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-200 hover:text-primary"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="w-6 h-6"
            >
              <path
                fill-rule="evenodd"
                d="M7.5 6a4.5 4.5 0 119 0 4.5 4.5 0 01-9 0zM3.751 20.105a8.25 8.25 0 0116.498 0 .75.75 0 01-.437.695A18.683 18.683 0 0112 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 01-.437-.695z"
                clip-rule="evenodd"
              />
            </svg>

            <span class="ml-3">면접관 관리</span>
          </a>
        </li>
        <!--        2. 질문관리-->
        <li>
          <a
            href="#"
            class="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="w-6 h-6"
            >
              <path
                fill-rule="evenodd"
                d="M7.502 6h7.128A3.375 3.375 0 0118 9.375v9.375a3 3 0 003-3V6.108c0-1.505-1.125-2.811-2.664-2.94a48.972 48.972 0 00-.673-.05A3 3 0 0015 1.5h-1.5a3 3 0 00-2.663 1.618c-.225.015-.45.032-.673.05C8.662 3.295 7.554 4.542 7.502 6zM13.5 3A1.5 1.5 0 0012 4.5h4.5A1.5 1.5 0 0015 3h-1.5z"
                clip-rule="evenodd"
              />
              <path
                fill-rule="evenodd"
                d="M3 9.375C3 8.339 3.84 7.5 4.875 7.5h9.75c1.036 0 1.875.84 1.875 1.875v11.25c0 1.035-.84 1.875-1.875 1.875h-9.75A1.875 1.875 0 013 20.625V9.375zM6 12a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75H6.75a.75.75 0 01-.75-.75V12zm2.25 0a.75.75 0 01.75-.75h3.75a.75.75 0 010 1.5H9a.75.75 0 01-.75-.75zM6 15a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75H6.75a.75.75 0 01-.75-.75V15zm2.25 0a.75.75 0 01.75-.75h3.75a.75.75 0 010 1.5H9a.75.75 0 01-.75-.75zM6 18a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75H6.75a.75.75 0 01-.75-.75V18zm2.25 0a.75.75 0 01.75-.75h3.75a.75.75 0 010 1.5H9a.75.75 0 01-.75-.75z"
                clip-rule="evenodd"
              />
            </svg>
            <span class="ml-3">질문 관리</span>
          </a>
        </li>
        <!--        3. 질문 카테고리 관리-->
        <li>
          <button
            @click="toggleQuestionCategory"
            type="button"
            class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-200 hover:text-primary"
            aria-controls="dropdown-example"
            data-collapse-toggle="dropdown-example"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="w-6 h-6"
            >
              <path
                fill-rule="evenodd"
                d="M5.25 2.25a3 3 0 00-3 3v4.318a3 3 0 00.879 2.121l9.58 9.581c.92.92 2.39 1.186 3.548.428a18.849 18.849 0 005.441-5.44c.758-1.16.492-2.629-.428-3.548l-9.58-9.581a3 3 0 00-2.122-.879H5.25zM6.375 7.5a1.125 1.125 0 100-2.25 1.125 1.125 0 000 2.25z"
                clip-rule="evenodd"
              />
            </svg>

            <span
              class="flex-1 ml-3 text-left whitespace-nowrap"
              sidebar-toggle-item
              >질문 카테고리 관리</span
            >
            <svg
              sidebar-toggle-item
              class="w-6 h-6"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                clip-rule="evenodd"
              ></path>
            </svg>
          </button>

          <ul :class="{ hidden: !questionCategoryOpen }" class="py-2 space-y-2">
            <li>
              <a
                href="#"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200"
                >직무 관리</a
              >
            </li>
            <li>
              <a
                href="#"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200"
                >유형 관리</a
              >
            </li>
          </ul>
        </li>

        <!--        4. 면접관리-->
        <li>
          <button
            @click="toggleInterview"
            type="button"
            class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-200 hover:text-primary"
            aria-controls="dropdown-example"
            data-collapse-toggle="dropdown-example"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="w-6 h-6"
            >
              <path
                d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32L19.513 8.2z"
              />
            </svg>

            <span class="flex-1 ml-3 text-left" sidebar-toggle-item
              >면접 관리</span
            >
            <svg
              sidebar-toggle-item
              class="w-6 h-6"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                clip-rule="evenodd"
              ></path>
            </svg>
          </button>
          <ul :class="{ hidden: !interviewOpen }" class="py-2 space-y-2">
            <li>
              <a
                href="#"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200"
                >면접 결과 관리</a
              >
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </aside>
</template>

<script setup>
import { ref } from "vue";

const questionCategoryOpen = ref(false);
const interviewOpen = ref(false);

const toggleQuestionCategory = () => {
  questionCategoryOpen.value = !questionCategoryOpen.value;
};

const toggleInterview = () => {
  interviewOpen.value = !interviewOpen.value;
};
</script>
