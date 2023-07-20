<template>
  <div id="gptLoadingModal" class="modal">
    <div class="modal-box">
      <h3 class="mb-8 font-bold text-xl text-center">답변 평가 요청 대기</h3>
      <div class="mb-8 text-lg">
        <p class="mb-4 text-center">
          Chat GPT에게 답변 평가 요청을 완료했습니다.
        </p>
        <p class="text-center">응답이 올 때 까지 잠시 기다려주세요😀</p>
      </div>
      <div class="flex justify-center mb-4">
        <span class="loading loading-dots loading-lg"></span>
      </div>
    </div>
  </div>
</template>

<script setup>
const toggleModal = () => {
  document.getElementById("gptLoadingModal").classList.toggle("modal-open");
};

defineExpose({
  toggleModal,
});
</script>
