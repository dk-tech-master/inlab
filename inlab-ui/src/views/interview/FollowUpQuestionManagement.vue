<template>
  <!--Header-->
  <header>
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">
        질문 관리 >
        <span class="font-medium text-indigo-500">꼬리 질문 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">
        꼬리 질문 관리
      </h2>
    </div>
  </header>

  <!--질문 제목-->
  <section class="mr-16 border border-gray-200 rounded-md p-10">
    <label
      for="searchInterview"
      class="block mb-2 text-base font-bold text-gray-700"
      >질문 제목</label
    >
    <h2 class="text-xl font-bold">{{ questionTitle }}</h2>
  </section>

  <!--질문 검색-->
  <div class="mr-16 mt-10 p-10 border border-gray-200 rounded-md">
    <div class="flex justify-between">
      <h2 class="mb-10 text-2xl font-bold">질문 검색</h2>
    </div>

    <div class="flex">
      <div class="mr-5 mb-5">
        <SearchFilter @search-info="handleSearch" />
      </div>
    </div>
    <section v-if="questionList.length > 0">
      <div
        class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg"
      >
        <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
          <div
            class="w-[40%] flex flex-col justify-center px-6 py-2 text-left"
          >
            질문 제목
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            직무
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            유형
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            난이도
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            버전
          </div>
          <div
            class="w-[10%] flex flex-col items-center justify-center px-6 py-2 text-left"
          ></div>
        </div>
        <div
          class="flex border-b hover:bg-gray-100"
          v-for="(question, index) in questionList"
          :key="index"
        >
          <div
            class="w-[40%] flex flex-col justify-center px-6 py-2 text-left"
          >
            {{ question.title }}
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ question.positionName }}
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ question.questionTypeName }}
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ question.questionLevelId }}
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ question.version }}
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-left"
          >
            <button
              class="customBtn"
              @click="addFollowUpQuestionBtn(question.questionId)"
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
    <EmptyState v-else :message="'질문이 존재하지 않습니다.'" />
  </div>

  <!--꼬리질문 리스트-->
  <div class="mr-16 mt-10 p-10 border border-gray-200 rounded-md">
    <div class="flex justify-between">
      <h2 class="mb-10 text-2xl font-bold">꼬리 질문 리스트</h2>
    </div>

    <section v-if="followUpQuestionList.length > 0">
      <div
        class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg"
      >
        <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
          <div class="w-[40%] flex flex-col justify-center px-6 py-2 text-left">
            질문 제목
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            직무
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            유형
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            난이도
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            버전
          </div>
          <div
            class="w-[10%] flex flex-col items-center justify-center px-6 py-2 text-left"
          ></div>
        </div>
        <div
          class="flex border-b hover:bg-gray-100"
          v-for="(followQuestion, index) in followUpQuestionList"
          :key="index"
        >
          <div
            class="w-[40%] flex flex-col justify-center px-6 py-2 text-left"
          >
            {{ followQuestion.title }}
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ followQuestion.positionName }}
          </div>
          <div
            class="w-[15%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ followQuestion.questionTypeName }}
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ followQuestion.questionLevelId }}
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-center"
          >
            {{ followQuestion.version }}
          </div>
          <div
            class="w-[10%] flex flex-col justify-center px-6 py-2 text-left"
          >
            <button
              class="customBtn2"
              @click="
                deleteFollowUpQuestionBtn(followQuestion.relatedQuestionId)
              "
            >
              삭제
            </button>
          </div>
        </div>
        <Pagination
          :paging-util="followUpQuestionPagingUtil"
          @changePage="changeFollowUpPage"
        />
      </div>
    </section>
    <EmptyState v-else :message="'등록된 꼬리질문이 존재하지 않습니다.'" />
  </div>
</template>

<script setup>
import {
  addFollowUpQuestion,
  deleteFollowUpQuestion,
  getFollowUpQuestion,
  getQuestion,
} from "@/api/question";
import { useRouter } from "vue-router";
import { ref } from "vue";
import SearchFilter from "@/components/common/SearchFilter.vue";
import Pagination from "@/components/common/Pagination.vue";
import EmptyState from "@/components/common/EmptyState.vue";

const questionTitle = ref("");
const router = useRouter();
const pagingInfos = ref({
  page: 1,
  pageSize: 5
});

const questionId = ref(null);
// const followUpQuestionId = ref(null);
const questionList = ref([]);
const followUpQuestionList = ref([]);
const pagingUtil = ref({});
const followUpQuestionPagingUtil = ref({});

const init = async () => {
  const params = ref(router.currentRoute.value.params);
  questionId.value = Number(params?.value?.questionId);
  const requestData = {
    page: pagingInfos.value.page,
    pageSize: pagingInfos.value.pageSize,
    questionId: isNaN(questionId.value) ? null : questionId.value
  };
  if (requestData.questionId) {
    // 전체질문조회
    const questionsInfos = await getQuestion(pagingInfos.value);
    questionList.value = questionsInfos.data.responseList;
    console.log(questionList.value);
    pagingUtil.value = questionsInfos.data.pagingUtil;

    // 꼬리질문조회
    const follwUpQuestionInfos = await getFollowUpQuestion(requestData);
    questionTitle.value = follwUpQuestionInfos.data.questionTitle;
    followUpQuestionList.value = follwUpQuestionInfos.data.responseList;
    followUpQuestionPagingUtil.value = follwUpQuestionInfos.data.pagingUtil;
    console.log("꼬리질문 데이터는 바로이거다.:", followUpQuestionList);
  }
};
init();

const requestSearchDate = ref({
  page: 1,
  pageSize: 5,
  positionId: null,
  questionLevelId: null,
  questionTypeId: null,
  titleKeyword: null
});

const handleSearch = async (searchInfos) => {
  const inputRequest = {
    page: 1,
    pageSize: 5,
    positionId: searchInfos.position,
    questionLevelId: searchInfos.level,
    questionTypeId: searchInfos.type,
    titleKeyword: searchInfos.title
  };
  requestSearchDate.value = inputRequest;
  console.log("요청데이터는 이거다 : ", requestSearchDate.value);
  const searchedInfos = await getQuestion(requestSearchDate.value);
  console.log("응답받은 데이터: ", searchedInfos);
  questionList.value = searchedInfos.data.responseList;
  pagingUtil.value = searchedInfos.data.pagingUtil;
};

const changePage = async (page) => {
  console.log(`changePage ${page}`);
  pagingUtil.value.page = page;
  requestSearchDate.value.page = page;
  const questionsInfos = await getQuestion(requestSearchDate.value);
  questionList.value = questionsInfos.data.responseList;
  console.log(questionsInfos.data);
  pagingUtil.value = questionsInfos.data.pagingUtil;
};

const changeFollowUpPage = async (page) => {
  console.log(`changePage ${page}`);
  followUpQuestionPagingUtil.value.page = page;
  const requestData = {
    page: followUpQuestionPagingUtil.value.page,
    pageSize: pagingInfos.value.pageSize,
    questionId: questionId.value
  };
  const followUpQuestionInfos = await getFollowUpQuestion(requestData);
  followUpQuestionList.value = followUpQuestionInfos.data.responseList;
  followUpQuestionPagingUtil.value = followUpQuestionInfos.data.pagingUtil;
};

const addFollowUpQuestionBtn = async (followUpId) => {
  const addRequestData = {
    headQuestionId: questionId.value,
    tailQuestionId: followUpId
  };
  const axiosResponse = await addFollowUpQuestion(addRequestData);

  if (axiosResponse.status >= 200 && axiosResponse.status < 300) {
    alert("꼬리 질문이 성공적으로 등록되었습니다.");
  }

  const requestData = {
    page: pagingInfos.value.page,
    pageSize: pagingInfos.value.pageSize,
    questionId: questionId.value
  };

  // 꼬리질문조회
  const follwUpQuestionInfos = await getFollowUpQuestion(requestData);
  followUpQuestionList.value = follwUpQuestionInfos.data.responseList;
  followUpQuestionPagingUtil.value = follwUpQuestionInfos.data.pagingUtil;
};

const deleteFollowUpQuestionBtn = async (id) => {
  const relatedQuestionId = id;
  console.log(relatedQuestionId);

  const axiosResponse = await deleteFollowUpQuestion(relatedQuestionId);
  console.log(axiosResponse);
  if (axiosResponse.status >= 200 && axiosResponse.status < 300) {
    alert("꼬리 질문이 성공적으로 삭제되었습니다.");
  } else {
    console.error("꼬리 질문 삭제을 실패했습니다.");
  }

  const requestData = {
    page: pagingInfos.value.page,
    pageSize: pagingInfos.value.pageSize,
    questionId: questionId.value
  };

  // 꼬리질문조회
  const follwUpQuestionInfos = await getFollowUpQuestion(requestData);
  followUpQuestionList.value = follwUpQuestionInfos.data.responseList;
  followUpQuestionPagingUtil.value = follwUpQuestionInfos.data.pagingUtil;
};
</script>
<style scoped></style>
