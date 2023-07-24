<template>
  <div id="clovaLoadingModal" class="modal">
    <div class="modal-box">
      <h3 class="mb-8 font-bold text-xl text-center">
        답변 텍스트 변환 요청 대기
      </h3>
      <div class="mb-8 text-lg">
        <p class="mb-4 text-center">면접자 답변을 텍스트로 변환중입니다.</p>
        <p class="text-center">완료될 때까지 잠시 기다려주세요😀</p>
      </div>
      <div class="flex justify-center mb-4">
        <span class="loading loading-dots loading-lg"></span>
      </div>
    </div>
  </div>
</template>

<script setup>
const toggleModal = () => {
  document.getElementById("clovaLoadingModal").classList.toggle("modal-open");
};

defineExpose({
  toggleModal,
});
</script>
