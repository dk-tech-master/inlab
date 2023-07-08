<template>
  <slot name="body"> </slot>
  <slot name="footer">
    <div class="flex items-end">
      <button class="flex flex-col ml-3 px-7 py-5 btn btn-primary btn-sm">
        검색
      </button>
    </div>
  </slot>
</template>

<script setup></script>
