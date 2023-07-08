<template>
  <button class="btn" onclick="my_modal_5.showModal()">open modal</button>
  <dialog id="my_modal_5" class="modal modal-bottom sm:modal-middle">
    <form method="dialog" class="px-10 py-10 modal-box">
      <slot name="header"></slot>
      <slot name="body"></slot>
      <slot name="footer"></slot>
    </form>
  </dialog>
</template>
<script setup></script>
