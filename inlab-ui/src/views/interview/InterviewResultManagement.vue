<template>
  <header>
    <div class="mb-10">
      <p class="mb-4 text-sm font-light text-gray-500">
        면접 관리 >
        <span class="font-medium text-indigo-500">면접 결과 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">
        면접 결과 관리
      </h2>
    </div>
    <div class="flex mb-8">
      <div class="flex gap-x-4">
        <div>
          <label
            for="searchInterview"
            class="block mb-2 text-base font-bold text-gray-700"
            >면접자 이름 검색</label
          >
          <input
            v-model="intervieweeName"
            type="text"
            name="interviewTitle"
            class="w-64 input input-bordered border-gray-300 text-sm"
            placeholder="면접자 이름을 입력하세요"
            @keyup.enter="clickSearchBtn"
          />
        </div>
        <div>
          <label class="block mb-2 text-base font-bold text-gray-700"
            >검색 기간 설정</label
          >
          <div class="flex gap-x-4 items-center">
            <input
              v-model="startDate"
              type="date"
              name="interviewTitle"
              class="input input-bordered border-gray-300 text-sm"
            />
            <span>~</span>
            <input
              v-model="endDate"
              type="date"
              name="interviewTitle"
              class="input input-bordered border-gray-300 text-sm"
            />
          </div>
        </div>
        <button class="btn btn-primary self-end" @click="clickSearchBtn">
          검색
        </button>
      </div>
    </div>
  </header>
  <section v-if="interviewResultList.length > 0">
    <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div
        class="flex items-center bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2"
      >
        <div class="w-[45%] flex flex-col justify-center py-4 text-left">
          면접 제목
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          면접자
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          면접관
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          면접일
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          결과 확인
        </div>
      </div>
      <div
        class="flex items-center border-b hover:bg-gray-100 gap-x-4 px-2"
        v-for="(item, index) in interviewResultList"
        :key="index"
      >
        <div class="w-[45%] flex flex-col justify-center py-4 text-left">
          {{ item.interviewTitle }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ item.intervieweeName }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ item.nickname }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ item.createdAt }}
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          <button
            type="button"
            class="btn btn-neutral btn-sm"
            @click="clickInterviewResultBtn(item.interviewResultId)"
          >
            결과 확인
          </button>
        </div>
      </div>
    </div>
    <div>
      <Pagination
        v-if="loaded"
        :paging-util="pagingUtil"
        @change-page="changePage"
      />
    </div>
  </section>
  <section v-else class="mt-8">
    <div class="border rounded-lg py-20">
      <div class="flex justify-center mb-10">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-20 h-20 text-gray-500"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M21 7.5l-9-5.25L3 7.5m18 0l-9 5.25m9-5.25v9l-9 5.25M3 7.5l9 5.25M3 7.5v9l9 5.25m0-9v9"
          />
        </svg>
      </div>
      <p class="text-lg text-gray-500 text-center">
        면접 결과가 존재하지 않습니다.
      </p>
    </div>
  </section>
</template>

<script setup>
import { getInterviewResultList } from "@/api/interviewResult";
import { ref } from "vue";
import { useRouter } from "vue-router";
import Pagination from "@/components/common/Pagination.vue";

const interviewResultList = ref([]);
const pagingUtil = ref({});
const intervieweeName = ref();
const startDate = ref();
const endDate = ref();
const loaded = ref(false);

const router = useRouter();

const init = async () => {
  const response = await getInterviewResultList();
  interviewResultList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
  console.log(response);
  console.log("interviewResultList: ", interviewResultList);
  loaded.value = true;
};
init();

const clickSearchBtn = async () => {
  const requestData = {
    intervieweeName: intervieweeName.value,
    startDate: startDate.value,
    endDate: endDate.value,
  };

  if (startDate.value && endDate.value && startDate.value > endDate.value) {
    alert("검색 기간을 다시 확인해주세요");
    startDate.value = "";
    endDate.value = "";
    return;
  }

  const response = await getInterviewResultList(requestData);
  interviewResultList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
  console.log("interviewResultList: ", interviewResultList);
};

const clickInterviewResultBtn = (interviewResultId) => {
  console.log("interviewResultId", interviewResultId);
  router.push(`/interview/result/${interviewResultId}`);
};

const changePage = async (page) => {
  console.log(`changePage ${page}`);
  const requestData = {
    intervieweeName: intervieweeName.value,
    startDate: startDate.value,
    endDate: endDate.value,
    page: page,
  };
  let response = await getInterviewResultList(requestData);
  interviewResultList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
};
</script>