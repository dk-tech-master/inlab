<template>
  <div>
    <header class="mt-8">
      <div class="mb-10">
        <p class="mb-1 text-sm font-light text-gray-500">
          질문 관리 >
          <span class="font-medium text-indigo-500">질문 버전 관리</span>
        </p>
        <h2 class="text-3xl tracking-tight font-bold text-gray-800">
          질문 버전 관리
        </h2>
      </div>
    </header>
    <section class="mr-16 mt-8">
      <div class="flex mb-10">
        <div>
          <label
            for="searchInterview"
            class="block mb-2 text-base font-bold text-gray-700"
            >질문 제목</label
          >
          <p
            class="flex justify-center items-center py-5 pr-36 bg-gray-50 text-primary input input-bordered input-sm text-lg"
          >
            {{ questionTitle }}
          </p>
        </div>
      </div>
      <div
        class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg"
      >
        <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
          <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
            제목
          </div>
          <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
            직무
          </div>
          <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
            유형
          </div>
          <div
            class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
          >
            난이도
          </div>
          <div
            class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
          >
            버전
          </div>
        </div>
        <div
          class="flex border-b hover:bg-gray-100"
          v-for="(question, index) in versionList"
          :key="index"
        >
          <div class="w-[30%] flex flex-col justify-center px-6 py-4 text-left">
            {{ question.title }}
          </div>
          <div class="w-[15%] flex flex-col justify-center px-6 py-4 text-left">
            {{ question.positionName }}
          </div>
          <div class="w-[20%] flex flex-col justify-center px-6 py-4 text-left">
            {{ question.questionTypeName }}
          </div>
          <div
            class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
          >
            {{ question.questionLevelName }}
          </div>
          <div
            class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
          >
            {{ question.version }}
          </div>
        </div>
      </div>
    </section>
    <section class="mt-20 mb-32">
      <Pagination :paging-util="pagingUtil" @changePage="changePage" />
    </section>
  </div>
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import { getQuestionVersion } from "@/api/question";
import { useRouter } from "vue-router";
import { ref } from "vue";

const router = useRouter();
const pagingInfos = ref({
  page: 1,
  pageSize: 7,
});

const pagingUtil = ref({});
const versionList = ref([]);
const questionId = ref(null);
const questionTitle = ref("");

const init = async () => {
  const params = ref(router.currentRoute.value.params);
  console.log(params.value.questionId); // 현재 questionId 값 확인
  questionId.value = params?.value?.questionId;
  const requestData = {
    page: pagingInfos.value.page,
    pageSize: pagingInfos.value.pageSize,
    questionId: questionId.value,
  };
  if (requestData.questionId) {
    // questionId가 존재하는 경우에만 실행
    const versionInfos = await getQuestionVersion(requestData);
    console.log(versionInfos);
    versionList.value = versionInfos.data.responseList;
    pagingUtil.value = versionInfos.data.pagingUtil;
    questionTitle.value = versionList.value[0].title;
  }
};

init();
const changePage = async (page) => {
  console.log(`changePage ${page}`);
  pagingUtil.value.page = page;
  const requestData = {
    page: pagingUtil.value.page,
    pageSize: pagingInfos.value.pageSize,
    questionId: questionId.value,
  };
  const questionsInfos = await getQuestionVersion(requestData);
  versionList.value = questionsInfos.data.responseList;
  console.log(questionsInfos.data);
  pagingUtil.value = questionsInfos.data.pagingUtil;
};
</script>
<style scoped></style>
