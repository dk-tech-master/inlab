<template>
  <dialog id="my_modal_5" class="modal modal-bottom sm:modal-middle">
    <form method="dialog" class="modal-box pt-12 py-8">
      <slot name="header"></slot>
      <slot name="body"></slot>
      <slot name="footer"></slot>
    </form>
  </dialog>
</template>
<script setup></script>

<style scoped></style>
