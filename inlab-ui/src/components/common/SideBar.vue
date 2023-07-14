<template>
  <aside
    id="sidebar-multi-level-sidebar"
    class="w-64 h-screen transition-transform -translate-x-full sm:translate-x-0"
    aria-label="Sidebar"
  >
    <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50">
      <ul class="space-y-2 font-medium">
        <li class="ml-2 mt-5 my-10">
          <div class="w-1/2">
            <img src="../../images/logo-color.svg" alt="logo" class="w-full" />
          </div>
          <p class="mt-3 ml-1 text-sm text-gray-500">면접 관리 시스템</p>
        </li>
        <!--        1. 면접관 관리-->
        <li>
          <a
            href="/interviewer"
            class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-200 hover:text-primary"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z"
              />
            </svg>

            <span class="ml-3">면접관 관리</span>
          </a>
        </li>
        <!--        2. 질문관리-->
        <li>
          <a
            href="/question"
            class="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 002.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 00-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 00.75-.75 2.25 2.25 0 00-.1-.664m-5.8 0A2.251 2.251 0 0113.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25zM6.75 12h.008v.008H6.75V12zm0 3h.008v.008H6.75V15zm0 3h.008v.008H6.75V18z"
              />
            </svg>

            <span class="ml-3">질문 관리</span>
          </a>
        </li>
        <!--        3. 질문 카테고리 관리-->
        <li>
          <button
            @click="toggleQuestionCategory(1)"
            type="button"
            class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-200 hover:text-primary"
            aria-controls="dropdown-example"
            data-collapse-toggle="dropdown-example"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M9.568 3H5.25A2.25 2.25 0 003 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 005.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 009.568 3z"
              />
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M6 6h.008v.008H6V6z"
              />
            </svg>

            <span
              class="flex-1 ml-3 text-left whitespace-nowrap mr-2"
              sidebar-toggle-item
              >질문 카테고리 관리
            </span>
            <svg
              :class="{ 'rotate-180': questionCategoryOpen }"
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
                href="/category/job"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-indigo-100"
                >직무 관리</a
              >
            </li>
            <li>
              <a
                href="/category/type"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-indigo-100"
                >유형 관리</a
              >
            </li>
          </ul>
        </li>

        <!--        4. 면접관리-->
        <li>
          <button
            @click="toggleQuestionCategory(2)"
            type="button"
            class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-200 hover:text-primary"
            aria-controls="dropdown-example"
            data-collapse-toggle="dropdown-example"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
              />
            </svg>

            <span class="flex-1 ml-3 text-left" sidebar-toggle-item
              >면접 관리</span
            >
            <svg
              :class="{ 'rotate-180': interviewOpen }"
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
                href="/interview"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-indigo-100"
                >면접 관리</a
              >
            </li>
            <li>
              <a
                href="/interview/result"
                class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-indigo-100"
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

const toggleQuestionCategory = (index) => {
  if (index === 1) {
    questionCategoryOpen.value = !questionCategoryOpen.value;
  } else if (index === 2) {
    interviewOpen.value = !interviewOpen.value;
  }
};

const activeMap = ref([
  { path: "interviewer", active: true },
  { path: "question", active: false },
  { path: "category", active: false },
  { path: "interviewer", active: false },
]);
</script>
<style scoped>
.rotate-180 {
  transform: rotate(180deg);
}
</style>
