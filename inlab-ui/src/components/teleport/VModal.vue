<template>
  <dialog id="modal" class="modal modal-bottom sm:modal-middle">
    <form method="dialog" class="px-10 py-10 modal-box">
      <slot name="header"></slot>
      <slot name="body"></slot>
      <slot name="footer" :toggleModal="toggleModal"></slot>
    </form>
  </dialog>
</template>

<script setup>
const toggleModal = () => {
  document.getElementById("modal").classList.toggle("modal-open");
};

defineExpose({
  toggleModal,
});
</script>
