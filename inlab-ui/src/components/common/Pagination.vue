<template>
  <nav aria-label="Page navigation" class="flex justify-center mt-10">
    <ul class="inline-flex">
      <li>
        <button
          class="h-10 px-5 text-primary transition-colors duration-150 bg-white rounded-l-lg focus:shadow-outline hover:bg-indigo-100"
        >
          Prev
        </button>
      </li>
      <li>
        <button
          class="h-10 px-5 text-white transition-colors duration-150 bg-indigo-600 focus:shadow-outline"
        >
          1
        </button>
      </li>
      <li>
        <button
          class="h-10 px-5 text-primary-600 transition-colors duration-150 bg-white focus:shadow-outline hover:bg-indigo-100"
        >
          2
        </button>
      </li>
      <li>
        <button
          class="h-10 px-5 text-indigo-600 transition-colors duration-150 bg-white focus:shadow-outline hover:bg-indigo-100"
        >
          3
        </button>
      </li>
      <li>
        <button
          class="h-10 px-5 text-indigo-600 transition-colors duration-150 bg-white rounded-r-lg focus:shadow-outline hover:bg-indigo-100"
        >
          Next
        </button>
      </li>
    </ul>
  </nav>
</template>
<script setup>
const clickPage = () => {
  console.log("해당페이지로 이동");
};

const clickPrePageGroup = () => {
  console.log("이전 페이지그룹으로 이동");
};

const clickNextPageGroup = () => {
  console.log("다음 페이지그룹으로 이동");
};
</script>
