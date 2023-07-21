<template>
  <header>
    <div class="mb-10">
      <p class="mb-4 text-sm font-light text-gray-500">
        질문 카테고리 관리 >
        <span class="font-medium text-indigo-500">유형 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">유형 관리</h2>
    </div>
    <div class="flex justify-between">
      <InputSearchFilter>
        <template v-slot:body>
          <div class="flex gap-x-4">
            <div>
              <label
                for="searchInterview"
                class="block mb-2 text-base font-bold text-gray-700"
                >직무</label
              >
              <select
                class="w-32 font-medium select select-primary border-gray-300"
                v-model="selectedPosition"
              >
                <option value="">ALL</option>
                <option
                  v-for="(item, index) in positionOptions"
                  :key="index"
                  :value="item.positionId"
                >
                  {{ item.positionName }}
                </option>
              </select>
            </div>
            <div>
              <label
                for="searchInterview"
                class="block mb-2 text-base font-bold text-gray-700"
                >유형명</label
              >
              <div class="flex">
                <input
                  type="text"
                  name="interviewTitle"
                  class="input input-bordered border-gray-300 text-sm"
                  placeholder="면접의 유형을 검색하세요."
                  v-model="searchInput"
                  @keyup.enter="handleSearch"
                  required
                />
              </div>
            </div>
            <div class="flex items-end">
              <button @click="handleSearch" class="btn btn-primary">
                검색
              </button>
            </div>
          </div>
          <div class="self-end">
            <button class="btn btn-primary" @click="clickCreateType">
              유형 등록
            </button>
          </div>
        </template>
      </InputSearchFilter>
    </div>
  </header>
  <section v-if="typeList.length > 0" class="mt-8">
    <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div
        class="flex items-center bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2"
      >
        <div class="w-[35%] flex flex-col justify-center py-4 text-left">
          직무
        </div>
        <div class="w-[35%] flex flex-col justify-center py-4 text-left">
          유형
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          질문 갯수
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          수정
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          삭제
        </div>
      </div>
      <div
        class="flex items-center border-b hover:bg-gray-100 gap-x-4 px-2"
        v-for="(item, index) in typeList"
        :key="index"
      >
        <div class="w-[35%] flex flex-col justify-center py-4 text-left">
          {{ item.positionName }}
        </div>
        <div class="w-[35%] flex flex-col justify-center py-4 text-left">
          {{ item.questionTypeName }}
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 py-4 text-left">
          {{ item.questionCount }}
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6 cursor-pointer"
            @click="clickUpdateType(index)"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
            />
          </svg>
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6 cursor-pointer"
            @click="clickDeleteType(item.questionTypeId)"
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
  <EmptyState v-else :message="'유형이 존재하지 않습니다.'" />
  <CreateTypeModal
    ref="createTypeModal"
    :positionOptions="positionOptions"
    @init="init"
  />
  <UpdateTypeModal
    ref="updateTypeModal"
    :positionOptions="positionOptions"
    @init="init"
  />
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
import CreateTypeModal from "@/components/modal/CreateTypeModal.vue";
import { ref } from "vue";
import { deleteTypes, getTypes } from "@/api/type";
import { getpositionOption } from "@/api/select";
import EmptyState from "@/components/common/EmptyState.vue";

const loaded = ref(false);
const pagingUtil = ref({});
const createTypeModal = ref(null);
const updateTypeModal = ref(null);
const typeList = ref([]);
const positionOptions = ref([]);
const searchInput = ref(null);
const selectedPosition = ref("");

const init = async () => {
  selectedPosition.value = "";
  searchInput.value = "";
  const responseType = await getTypes();
  typeList.value = responseType.data.responseList;
  pagingUtil.value = responseType.data.pagingUtil;

  const responseOption = await getpositionOption();
  positionOptions.value = responseOption.data;
  loaded.value = true;
};

init();

const handleSearch = async () => {
  const requestData = {
    positionId: selectedPosition.value,
    questionTypeName: searchInput.value,
  };
  const response = await getTypes(requestData);
  typeList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;

  console.log(response.data);
};

const clickCreateType = () => {
  console.log("clickType");
  createTypeModal.value.toggleModal();
};

const clickUpdateType = (index) => {
  let typeData = typeList.value[index];
  updateTypeModal.value.toggleModal(typeData);
};

const clickDeleteType = async (typeId) => {
  const flag = window.confirm("유형을 삭제하시겠습니까?");
  if (!flag) {
    return;
  }
  await deleteTypes(typeId);
  init();
};

const changePage = async (page) => {
  console.log(`changePage ${page}`);
  const requestData = {
    page: page,
    positionId: selectedPosition.value,
    questionTypeName: searchInput.value,
  };
  let response = await getTypes(requestData);
  typeList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
};
</script>
<style scoped></style>