<template>
  <div>
    <header class="mt-8">
      <div class="mb-10">
        <p class="mb-1 text-sm font-light text-gray-500">
          면접 관리 > 면접 상세 >
          <span class="font-medium text-indigo-500">면접 질문 관리</span>
        </p>
        <h2 class="text-3xl tracking-tight font-bold text-gray-800">
          면접 질문 관리
        </h2>
      </div>
    </header>
    <section class="mr-16 mt-8 px-10 py-10 shadow-md">
      <div class="flex justify-between">
        <h2 class="mb-10 text-2xl font-bold">질문 검색</h2>
      </div>
      <div class="flex">
        <div class="mr-5 mb-5">
          <SearchFilter @search-info="handleSearch" />
        </div>
      </div>

      <div
        class="mt-3 table flex text-left w-full overflow-x-auto sm:rounded-lg"
      >
        <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
          <div class="w-[35%] flex flex-col justify-center px-6 py-2 text-left">
            제목
          </div>
          <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
            직무
          </div>
          <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
            유형
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            난이도
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            버전
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            추가
          </div>
        </div>
        <div
          class="flex border-b hover:bg-gray-100"
          v-for="question in questionList"
          :key="question.questionId"
        >
          <div class="w-[35%] flex flex-col justify-center px-6 py-2 text-left">
            {{ question.title }}
          </div>
          <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
            {{ question.positionName }}
          </div>
          <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
            {{ question.questionTypeName }}
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            {{ question.questionLevelName }}
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            {{ question.version }}
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            <button
              class="customBtn"
              @click="addInterviewQuestionBtn(question.questionId)"
            >
              추가
            </button>
          </div>
        </div>
      </div>

      <div>
        <Pagination :paging-util="pagingUtil" @changePage="changePage" />
      </div>
    </section>

    <section class="mr-16 mt-8 px-10 py-12 shadow-md">
      <div class="flex justify-between">
        <h2 class="mb-10 text-2xl font-bold">면접 질문 리스트</h2>
      </div>

      <div
        class="mt-3 table flex text-left w-full overflow-x-auto sm:rounded-lg"
      >
        <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
          <div class="w-[35%] flex flex-col justify-center px-6 py-2 text-left">
            제목
          </div>
          <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
            직무
          </div>
          <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
            유형
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            난이도
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            버전
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            추가
          </div>
        </div>
        <div
          class="flex border-b hover:bg-gray-100"
          v-for="interviewQuestion in interviewQuestionList"
          :key="interviewQuestion.questionId"
        >
          <div class="w-[35%] flex flex-col justify-center px-6 py-2 text-left">
            {{ interviewQuestion.questionTitle }}
          </div>
          <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
            {{ interviewQuestion.positionName }}
          </div>
          <div class="w-[25%] flex flex-col justify-center px-6 py-2 text-left">
            {{ interviewQuestion.questionTypeName }}
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            {{ interviewQuestion.positionName }}
          </div>
          <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
            <button
              class="customBtn2"
              @click="
                deleteInterviewQuestionBtn(
                  interviewQuestion.interviewQuestionId,
                )
              "
            >
              삭제
            </button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import {
  deleteInterviewQuestion,
  getInterviewQuestionInfo,
} from "@/api/interview";
import { getQuestion } from "@/api/question";
import SearchFilter from "@/components/common/SearchFilter.vue";

const interviewId = ref(null);
const questionList = ref([]);
const interviewQuestionList = ref([]);
const pagingUtil = ref({});

const router = useRouter();
const pagingInfos = ref({
  page: 1,
  pageSize: 5,
});

const requestSearchData = ref({
  page: 1,
  pageSize: 5,
  positionId: null,
  questionLevelId: null,
  questionTypeId: null,
  titleKeyword: null,
});

const init = async () => {
  //전체 질문 리스트
  const questionsInfos = await getQuestion(pagingInfos.value);
  questionList.value = questionsInfos.data.responseList;
  console.log(questionList.value);
  pagingUtil.value = questionsInfos.data.pagingUtil;

  //면접 질문 리스트
  const params = ref(router.currentRoute.value.params);
  interviewId.value = params.value.interviewId;
  console.log(interviewId.value);
  const interviewQuestionInfos = await getInterviewQuestionInfo(
    interviewId.value,
  );

  console.log(interviewQuestionInfos);
  interviewQuestionList.value = interviewQuestionInfos.data;
  console.log(interviewQuestionList.value);
};

init();

const changePage = async (page) => {
  console.log(`changePage ${page}`);
  pagingUtil.value.page = page;
  requestSearchData.value.page = page;
  const questionsInfos = await getQuestion(requestSearchData.value);
  questionList.value = questionsInfos.data.responseList;
  console.log(questionsInfos.data);
  pagingUtil.value = questionsInfos.data.pagingUtil;
};

const handleSearch = async (searchInfos) => {
  const inputRequest = {
    page: 1,
    pageSize: 5,
    positionId: searchInfos.position,
    questionLevelId: searchInfos.level,
    questionTypeId: searchInfos.type,
    titleKeyword: searchInfos.title,
  };
  requestSearchData.value = inputRequest;
  console.log("요청데이터는 이거다 : ", requestSearchData.value);
  const searchedInfos = await getQuestion(requestSearchData.value);
  console.log("응답받은 데이터: ", searchedInfos);
  questionList.value = searchedInfos.data.responseList;
  pagingUtil.value = searchedInfos.data.pagingUtil;
};

const addInterviewQuestionBtn = async (questionId, versionId) => {
  const addRequestData = {
    interviewId: interviewId.value,
    questionId: questionId.value,
    questionVersionId: versionId,
  };

  const interviewQuestionInfos = await getInterviewQuestionInfo(
    interviewId.value,
  );
  interviewQuestionList.value = interviewQuestionInfos.data;
};

const deleteInterviewQuestionBtn = async (questionId) => {
  console.log(questionId);
  if (confirm("정말 삭제하시겠습니까?")) {
    const axiosResponse = await deleteInterviewQuestion(questionId);
    console.log(axiosResponse);
    const interviewQuestionInfos = await getInterviewQuestionInfo(
      interviewId.value,
    );
    interviewQuestionList.value = interviewQuestionInfos.data;
  }
};
</script>
<style scoped></style>
