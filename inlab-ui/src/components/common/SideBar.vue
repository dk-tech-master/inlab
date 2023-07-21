<template>
  <aside
    id="sidebar-multi-level-sidebar"
    class="w-72 min-h-screen transition-transform -translate-x-full sm:translate-x-0"
    aria-label="Sidebar"
  >
    <div class="h-full overflow-y-auto bg-gray-100 border-r p-4">
      <ul class="space-y-4 font-medium">
        <li class="mb-8">
          <div class="w-1/2 cursor-pointer" @click="clickHomeBtn">
            <img src="../../images/logo-color.svg" alt="logo" class="w-full" />
          </div>
          <p class="mt-3 ml-1 text-sm text-gray-500">면접 질문 관리 시스템</p>
        </li>
        <!--        1. 질문관리-->
        <li>
          <a
            href="/question"
            :class="
              activeMap[0].active
                ? `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                : `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary`
            "
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
        <!--        2. 면접관리-->
        <li>
          <a
            href="/interview"
            :class="
              activeMap[1].active
                ? `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                : `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary`
            "
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
                d="M20.25 8.511c.884.284 1.5 1.128 1.5 2.097v4.286c0 1.136-.847 2.1-1.98 2.193-.34.027-.68.052-1.02.072v3.091l-3-3c-1.354 0-2.694-.055-4.02-.163a2.115 2.115 0 01-.825-.242m9.345-8.334a2.126 2.126 0 00-.476-.095 48.64 48.64 0 00-8.048 0c-1.131.094-1.976 1.057-1.976 2.192v4.286c0 .837.46 1.58 1.155 1.951m9.345-8.334V6.637c0-1.621-1.152-3.026-2.76-3.235A48.455 48.455 0 0011.25 3c-2.115 0-4.198.137-6.24.402-1.608.209-2.76 1.614-2.76 3.235v6.226c0 1.621 1.152 3.026 2.76 3.235.577.075 1.157.14 1.74.194V21l4.155-4.155"
              />
            </svg>

            <span class="ml-3">면접 관리</span>
          </a>
        </li>
        <li>
          <a
            href="/interview/result"
            :class="
              activeMap[2].active
                ? `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                : `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary`
            "
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
                d="M10.125 2.25h-4.5c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125v-9M10.125 2.25h.375a9 9 0 019 9v.375M10.125 2.25A3.375 3.375 0 0113.5 5.625v1.5c0 .621.504 1.125 1.125 1.125h1.5a3.375 3.375 0 013.375 3.375M9 15l2.25 2.25L15 12"
              />
            </svg>

            <span class="ml-3">면접 결과 관리</span>
          </a>
        </li>
        <!--        3. 면접관 관리-->
        <li v-if="role === 'ROLE_ADMIN'">
          <a
            href="/interviewer"
            :class="
              activeMap[3].active
                ? `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                : `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary`
            "
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
        <!--        4. 질문 카테고리 관리-->
        <li v-if="role === 'ROLE_ADMIN'">
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
                :class="
                  activeMap[4].active
                    ? `flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                    : `flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200 hover:text-primary`
                "
                >직무 관리</a
              >
            </li>
            <li>
              <a
                href="/category/type"
                :class="
                  activeMap[5].active
                    ? `flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                    : `flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-200 hover:text-primary`
                "
                >유형 관리</a
              >
            </li>
          </ul>
        </li>
        <!--        5. 질문열람 내역-->
        <li v-if="role === 'ROLE_ADMIN'">
          <a
            href="/user-question-history"
            :class="
              activeMap[6].active
                ? `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary bg-gray-200 text-primary`
                : `flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-200 hover:text-primary`
            "
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

            <span class="ml-3">질문 열람 내역</span>
          </a>
        </li>
      </ul>
    </div>
  </aside>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const questionCategoryOpen = ref(false);
const interviewOpen = ref(false);
const role = sessionStorage.getItem("role");
const activeMap = ref([
  {
    path: "/question",
    active: true,
  },
  {
    path: "/interview",
    active: false,
  },
  {
    path: "/interview/result",
    active: false,
  },
  {
    path: "/interviewer",
    active: false,
  },
  {
    path: "/category/job",
    active: false,
  },
  {
    path: "/category/type",
    active: false,
  },
  {
    path: "/user-question-history",
    active: false,
  },
]);

const routeKey = route.path;
console.log(routeKey);
activeMap.value.forEach((item) => {
  if (item.path === routeKey) {
    if (item.path === "/category/job" || item.path === "/category/type") {
      questionCategoryOpen.value = true;
    }
    item.active = true;
  } else {
    item.active = false;
  }
});

const toggleQuestionCategory = (index) => {
  if (index === 1) {
    questionCategoryOpen.value = !questionCategoryOpen.value;
  } else if (index === 2) {
    interviewOpen.value = !interviewOpen.value;
  }
};

const clickHomeBtn = async () => {
  await router.push("/question");
  location.reload();
};
</script>
<style scoped>
.rotate-180 {
  transform: rotate(180deg);
}
</style>