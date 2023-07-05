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
        >
          <option value="프론트앤드">프론트엔드</option>
          <option value="백엔드">백엔드</option>
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
        >
          <option value="유형1">프로그래밍 언어</option>
          <option value="유형2">운영체제</option>
          <option value="유형3">프레임워크</option>
          <option value="유형4">데이터베이스</option>
          <option value="유형5">네트워크</option>
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
        >
          <option value="all">All</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
        </select>
      </div>
      <div class="mr-3">
        <label
          for="nickName"
          class="block mb-2 text-sm font-medium text-gray-900"
          >닉네임</label
        >
        <input
          type="text"
          name="nickName"
          class="p-2.5 input input-sm input-bordered border-gray-300 text-sm"
          placeholder="선데이"
          required=""
        />
        <button class="ml-3 px-5 btn btn-primary btn-sm">검색</button>
      </div>
    </div>
  </slot>
</template>
<script setup></script>
<style scoped></style>
