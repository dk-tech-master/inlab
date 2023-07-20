<template>
  <header>
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">
        질문 카테고리 관리 >
        <span class="font-medium text-indigo-500">직무 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">직무 관리</h2>
    </div>
    <div class="flex justify-between">
      <InputSearchFilter>
        <template v-slot:body>
          <div class="flex">
            <div>
              <label
                for="searchInterview"
                class="block mb-2 text-base font-bold text-gray-700"
                >직무 검색</label
              >
              <div class="flex">
                <input
                  type="text"
                  name="interviewTitle"
                  class="w-64 py-5 input input-bordered input-sm border-gray-300 text-sm"
                  placeholder="면접의 직무를 검색하세요"
                  v-model="searchInput"
                  @keyup.enter="handleSearch"
                  required
                />
              </div>
            </div>
            <div class="flex items-end">
              <button
                @click="handleSearch"
                class="flex flex-col ml-3 px-5 py-5 btn btn-primary btn-sm"
              >
                검색
              </button>
            </div>
          </div>
          <div class="self-end">
            <button
              class="btn btn-primary btn-sm ml-auto flex flex-col items-center ml-3 px-5 py-5"
              @click="clickCreateJob"
            >
              직무 등록
            </button>
          </div>
        </template>
      </InputSearchFilter>
    </div>
  </header>
  <section v-if="jobList.length > 0" class="mt-8">
    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[35%] flex flex-col justify-center px-6 py-2 text-left">
          직무
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          질문 갯수
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          수정
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          삭제
        </div>
      </div>
      <div
        class="flex border-b hover:bg-gray-100"
        v-for="(item, index) in jobList"
        :key="item"
      >
        <div class="w-[35%] flex flex-col justify-center px-6 py-4 text-left">
          {{ item.positionName }}
        </div>
        <div class="w-[20%] flex flex-col justify-center px-10 py-4 text-left">
          {{ item.questionCount }}
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-4 text-left">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6 cursor-pointer"
            @click="clickUpdateJob(index)"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
            />
          </svg>
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6 cursor-pointer"
            @click="clickDeleteJob(item.positionId)"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
            />
          </svg>
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
  <EmptyState v-else :message="'직무가 존재하지 않습니다.'" />
  <CreateJobModal ref="createJobModal" @init="init" />
  <UpdateJobModal ref="updateJobModal" @init="init" />
</template>

<script setup>
import { ref } from "vue";
import { deleteJobs, getJobs } from "@/api/job";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
import CreateJobModal from "@/components/modal/CreateJobModal.vue";
import UpdateJobModal from "@/components/modal/UpdateJobModal.vue";
import Pagination from "@/components/common/Pagination.vue";
import EmptyState from "@/components/common/EmptyState.vue";

const loaded = ref(false);
const createJobModal = ref(null);
const updateJobModal = ref(null);
const pagingUtil = ref({});
const jobList = ref([]);
const searchInput = ref("");

const init = async () => {
  const jobInfo = await getJobs();
  jobList.value = jobInfo.data.responseList;
  pagingUtil.value = jobInfo.data.pagingUtil;
  loaded.value = true;
  console.log(jobInfo.data);
};

init();

const handleSearch = async () => {
  const requestData = {
    page: 1,
    positionName: searchInput.value,
  };
  const jobInfo = await getJobs(requestData);
  jobList.value = jobInfo.data.responseList;
  pagingUtil.value = jobInfo.data.pagingUtil;
};

const clickDeleteJob = async (jobId) => {
  window.confirm("직무를 삭제하시겠습니까?");
  await deleteJobs(jobId);
  init();
};

const clickCreateJob = () => {
  console.log("clickCreateJob");
  createJobModal.value.toggleModal();
};

const clickUpdateJob = (index) => {
  console.log("clickUpdateJob");
  let jobData = jobList.value[index];
  updateJobModal.value.toggleModal(jobData);
};

const changePage = async (page) => {
  console.log(`changePage ${page}`);
  const requestData = {
    page: page,
    positionName: searchInput.value,
  };
  let response = await getJobs(requestData);
  jobList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
};
</script>