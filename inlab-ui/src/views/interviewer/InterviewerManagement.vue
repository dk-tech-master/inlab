<template>
  <div>
    <header>
      <div class="mb-10">
        <p class="mb-4 text-sm font-medium text-indigo-500">면접관 관리</p>
        <h2 class="text-3xl tracking-tight font-bold text-gray-800">
          면접관 관리
        </h2>
      </div>
      <div class="flex mb-8">
        <div class="flex gap-x-4">
          <InputSearchFilter>
            <template v-slot:body>
              <div>
                <label
                  for="searchInterview"
                  class="block mb-2 text-base font-bold text-gray-700"
                  >닉네임</label
                >
                <input
                  type="text"
                  v-model="pagingUtil.nickname"
                  name="interviewTitle"
                  class="w-64 input input-bordered border-gray-300 text-sm"
                  placeholder="닉네임을 입력하세요"
                  @keyup.enter="init"
                />
              </div>
              <div>
                <label
                  for="isApprovedAccount"
                  class="block mb-2 text-base font-bold text-gray-700"
                  >회원가입 승인</label
                >
                <select
                  class="w-32 text-sm font-medium select select-primary border-gray-300"
                  v-model="pagingUtil.isVerified"
                >
                  <option value="true">승인</option>
                  <option value="false">미승인</option>
                </select>
              </div>
            </template>

            <template v-slot:footer>
              <div class="flex items-end gap-x-4">
                <button @click="init" class="btn btn-primary">검색</button>
              </div>
            </template>
          </InputSearchFilter>
        </div>
      </div>
    </header>
    <template v-if="infos.length > 0">
      <section>
        <div
          class="table flex items-center flex-col w-full overflow-x-auto sm:rounded-lg"
        >
          <div
            class="flex bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2"
          >
            <div class="w-[30%] flex flex-col justify-center py-4 text-left">
              이메일
            </div>
            <div class="w-[15%] flex flex-col justify-center py-4 text-left">
              닉네임
            </div>
            <div class="w-[15%] flex flex-col justify-center py-4 text-left">
              가입일
            </div>
            <div
              class="w-[15%] flex flex-col justify-center items-center py-4 text-left"
            >
              회원가입 승인
            </div>
            <div class="w-[15%] flex flex-col justify-center py-4 text-left">
              질문 접근 권한
            </div>
            <div class="w-[10%] flex flex-col justify-center py-4 text-left">
              삭제
            </div>
          </div>

          <div
            class="flex items-center border-b hover:bg-gray-100 gap-x-4 px-2"
            v-for="(info, index) in infos"
            :key="index"
          >
            <div class="w-[30%] flex flex-col justify-center py-4 text-left">
              {{ info.email }}
            </div>
            <div class="w-[15%] flex flex-col justify-center py-4 text-left">
              {{ info.nickname }}
            </div>
            <div class="w-[15%] flex flex-col justify-center py-4 text-left">
              {{ info.createdAt.substring(0, 16).replace("T", " ") }}
            </div>
            <div
              class="w-[15%] flex flex-col justify-center items-center py-4 text-left"
            >
              <div v-if="info.isVerified">
                <p class="text-green-500 font-semibold">승인됨</p>
              </div>
              <div v-else>
                <button
                  @click="toggleApproval(info.userId, index)"
                  class="btn btn-error btn-sm btn-outline"
                >
                  미승인
                </button>
              </div>
            </div>
            <div class="w-[15%] flex justify-start py-4 text-left">
              <div>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6 cursor-pointer"
                  @click="openModal(info.userId)"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M8.25 6.75h12M8.25 12h12m-12 5.25h12M3.75 6.75h.007v.008H3.75V6.75zm.375 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zM3.75 12h.007v.008H3.75V12zm.375 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm-.375 5.25h.007v.008H3.75v-.008zm.375 0a.375.375 0 11-.75 0 .375.375 0 01.75 0z"
                  />
                </svg>
              </div>
              <div>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="ml-5 w-6 h-6 cursor-pointer"
                  @click="openAddModal(info.userId)"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.324.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 011.37.49l1.296 2.247a1.125 1.125 0 01-.26 1.431l-1.003.827c-.293.24-.438.613-.431.992a6.759 6.759 0 010 .255c-.007.378.138.75.43.99l1.005.828c.424.35.534.954.26 1.43l-1.298 2.247a1.125 1.125 0 01-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.57 6.57 0 01-.22.128c-.331.183-.581.495-.644.869l-.213 1.28c-.09.543-.56.941-1.11.941h-2.594c-.55 0-1.02-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 01-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 01-1.369-.49l-1.297-2.247a1.125 1.125 0 01.26-1.431l1.004-.827c.292-.24.437-.613.43-.992a6.932 6.932 0 010-.255c.007-.378-.138-.75-.43-.99l-1.004-.828a1.125 1.125 0 01-.26-1.43l1.297-2.247a1.125 1.125 0 011.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.087.22-.128.332-.183.582-.495.644-.869l.214-1.281z"
                  />
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="w-[10%] flex flex-col justify-center py-4 text-left">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6 cursor-pointer"
                @click="deleteInterviewerBtn(info.userId)"
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
      </section>
      <Pagination :paging-util="pagingUtil" @changePage="changePage" />
    </template>
    <template v-else>
      <EmptyState :message="`면접관이 존재하지 않습니다.`" />
    </template>
    <QuestionVerificationModal ref="verificationModal" />
    <QuestionVerificationAddModal ref="verificationAddModal" />
  </div>
</template>

<script setup>
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
import QuestionVerificationModal from "@/components/modal/QuestionVerificationModal.vue";
import QuestionVerificationAddModal from "@/components/modal/QuestionVerificationAddModal.vue";
import { getInterviewers, updateApprove } from "@/api/interviewer";
import { ref } from "vue";
import Pagination from "@/components/common/Pagination.vue";
import { deleteUser } from "@/api/auth";
import EmptyState from "@/components/common/EmptyState.vue";

const pagingUtil = ref({
  page: 1,
  pageSize: 5,
  nickname: "",
  isVerified: "",
});

const infos = ref([]);
const verificationModal = ref(null);
const verificationAddModal = ref(null);

const init = async () => {
  const requestData = {
    page: pagingUtil.value.page,
    pageSize: pagingUtil.value.pageSize,
    nickname: pagingUtil.value.nickname,
    isVerified: pagingUtil.value.isVerified,
  };
  const interviewersInfo = await getInterviewers(requestData);
  infos.value = interviewersInfo.data.responseList;
  pagingUtil.value = interviewersInfo.data.pagingUtil;
  pagingUtil.value.nickname = requestData.nickname;
  pagingUtil.value.isVerified = requestData.isVerified;
  console.log(infos.value);
};

init();

const toggleApproval = async (userId) => {
  console.log(userId);
  const axiosResponse = await updateApprove(userId);
  init();
};

const deleteInterviewerBtn = async (userId) => {
  console.log(userId);
  if (confirm("정말 삭제하시겠습니끼?")) {
    const axiosResponse = await deleteUser(userId);
    console.log(axiosResponse);
    // if (axiosResponse.status === 200) {
    //   for (let i = 0; i < infos.value.length; i++) {
    //     if (infos[i].value.userId === userId) {
    //       infos.value.splice(i, 1);
    //       break;
    //     }
    //   }
    // }
    const requestData = {
      page: pagingUtil.value.page,
      pageSize: pagingUtil.value.pageSize,
      nickname: "",
      isVerified: "",
    };
    const interviewersInfo = await getInterviewers(requestData);
    infos.value = interviewersInfo.data.responseList;
    pagingUtil.value = interviewersInfo.data.pagingUtil;
  }
};

const openModal = (userId) => {
  console.log("oooooooooooooo");
  verificationModal.value.toggleModal(userId);
};

const openAddModal = (userId) => {
  console.log("xxxxxxxxxxxxx");
  verificationAddModal.value.toggleModal(userId);
};

const changePage = async (page) => {
  pagingUtil.value.page = page;
  const requestData = {
    page: pagingUtil.value.page,
    pageSize: pagingUtil.value.pageSize,
    nickname: "",
    isVerified: "",
  };
  const interviewersInfo = await getInterviewers(requestData);
  console.log(interviewersInfo);
  infos.value = interviewersInfo.data.responseList;
  pagingUtil.value = interviewersInfo.data.pagingUtil;
};

const resetPage = () => {
  pagingUtil.value.nickname = "";
  pagingUtil.value.isVerified = "";
  init();
};
</script>
