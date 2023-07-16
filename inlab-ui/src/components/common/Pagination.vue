<template>
  <nav aria-label="Page navigation" class="flex justify-center mt-10">
    <ul class="inline-flex">
      <li>
        <a
          v-if="
            pagingUtil.startPage == pagingUtil.pageNumber &&
            !pagingUtil.existPrePageGroup
          "
          class="h-10 px-5 text-primary transition-colors duration-150 bg-white rounded-l-lg focus:shadow-outline hover:bg-indigo-100"
        >
          Prev
        </a>
        <a
          v-else
          @click="clickPrePageGroup"
          class="h-10 px-5 text-primary transition-colors duration-150 bg-white rounded-l-lg focus:shadow-outline hover:bg-indigo-100"
        >
          Prev
        </a>
      </li>
      <li
        v-for="page in props.pagingUtil.endPage +
        1 -
        props.pagingUtil.startPage"
        :key="page + pagingUtil.startPage"
      >
        <a
          v-if="pagingUtil.pageNumber == page + pagingUtil.startPage - 1"
          @click="clickPage(page + pagingUtil.startPage - 1)"
          class="relative z-10 inline-flex items-center bg-[#f9d72f] px-4 py-2 text-sm font-semibold text-black focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-gray-700"
        >
          {{ page + pagingUtil.startPage - 1 }}
        </a>
        <a
          v-else
          aria-current="page"
          @click="clickPage(page + pagingUtil.startPage - 1)"
          class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 cursor-pointer"
        >
          {{ page + pagingUtil.startPage - 1 }}
        </a>
      </li>
      <li>
        <a
          v-if="
            pagingUtil.endPage == pagingUtil.pageNumber &&
            !pageUtil.existNextPageGroup
          "
          class="h-10 px-5 text-indigo-600 transition-colors duration-150 bg-white rounded-r-lg focus:shadow-outline hover:bg-indigo-100"
        >
          Next
        </a>
        <a
          v-else
          @click="clickNextPageGroup"
          class="h-10 px-5 text-indigo-600 transition-colors duration-150 bg-white rounded-r-lg focus:shadow-outline hover:bg-indigo-100"
        >
          Next
        </a>
      </li>
    </ul>
  </nav>
</template>
<script setup>
const props = defineProps({
  pagingUtil: Object,
});

const emit = defineEmits(["change-page"]);
const clickPage = (page) => {
  emit("change-page", page);
};

const clickPrePageGroup = () => {
  emit("change-page", props.pagingUtil.pageNumber - 1);
};

const clickNextPageGroup = () => {
  emit("change-page", props.pagingUtil.pageNumber + 1);
};
</script>
