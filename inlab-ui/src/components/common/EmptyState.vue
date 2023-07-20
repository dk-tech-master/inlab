<template>
  <section class="mr-16 mt-8">
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
      <p class="text-lg text-gray-500 text-center">{{ props.message }}</p>
    </div>
  </section>
</template>

<script setup>
const props = defineProps({
  message: String,
});
</script>