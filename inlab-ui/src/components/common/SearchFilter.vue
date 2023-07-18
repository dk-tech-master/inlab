<template>
  <slot name="header"></slot>
  <slot name="body">
    <div class="flex justify mt-5">
      <div class="mr-3">
        <label for="job" class="block mb-2 text-sm font-medium text-gray-900"
          >직무</label
        >
        <select
          class="font-medium select select-primary select-sm border-gray-300 max-w-xs"
          v-model="searchInfo.position"
        >
          <option value="6">프론트엔드</option>
          <option value="1">백엔드</option>
        </select>
      </div>
      <div class="mr-3">
        <label
          for="questionType"
          class="block mb-2 text-sm font-medium text-gray-900"
          >유형</label
        >
        <select
          class="font-medium select select-primary select-sm border-gray-300 max-w-xs"
          v-model="searchInfo.type"
        >
          <option value="1">운영체제</option>
          <option value="2">실무경험 및 프로젝트 질문</option>
          <option value="3">문제 해결 능력 질문</option>
          <option value="4">팀워크 및 소통 질문</option>
          <option value="7">프로그래밍 언어</option>
          <option value="8">데이터베이스</option>
          <option value="9">네트워크</option>
        </select>
      </div>
      <div class="mr-3">
        <label
          for="questionLevel"
          class="block mb-2 text-sm font-medium text-gray-900"
          >난이도</label
        >
        <select
          class="font-medium select select-primary select-sm border-gray-300 max-w-xs"
          v-model="searchInfo.level"
        >
          <option value="">All</option>
          <option value="3">상</option>
          <option value="2">중</option>
          <option value="1">하</option>
        </select>
      </div>
      <div class="mr-3">
        <label
          for="nickName"
          class="block mb-2 text-sm font-medium text-gray-900"
          >제목</label
        >
        <input
          v-model="searchInfo.title"
          type="text"
          name="title"
          class="p-2.5 input input-sm input-bordered border-gray-300 text-sm"
          placeholder="제목을 입력하세요"
          required
          @keyup.enter="onSearch"
        />
        <button class="ml-3 px-5 btn btn-primary btn-sm" @click="onSearch">
          검색
        </button>
      </div>
    </div>
  </slot>
</template>
<script setup>
import { ref } from "vue";

const searchInfo = ref({
  position: "",
  type: "",
  level: "",
  title: "",
});

const emit = defineEmits(["search-info"]);

const onSearch = () => {
  //검색 이벤트 발생
  emit("search-info", searchInfo.value);
};
</script>
