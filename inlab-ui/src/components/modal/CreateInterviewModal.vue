<template>
  <dialog id="createInterviewModal" class="modal">
    <form method="dialog" class="modal-box">
      <h2 class="font-bold text-lg mb-6">면접 등록</h2>
      <input
        v-model="interviewTitle"
        type="text"
        name="registerInterview"
        class="input input-bordered w-full mr-2 border-gray-300 text-sm"
        placeholder="면접 제목을 입력해주세요."
        required
      />
      <div class="modal-action">
        <button
          class="flex flex-col mr-3 py-5 px-5 btn btn-sm btn-primary btn-outline"
          type="button"
          @click="toggleModal"
        >
          취소
        </button>
        <button
          type="button"
          class="flex flex-col py-5 px-5 btn btn-sm btn-primary"
          @click="clickCreateInterviewBtn"
        >
          확인
        </button>
      </div>
    </form>
  </dialog>
</template>

<script setup>
import { ref, defineEmits } from "vue";
import { createInterview } from "@/api/interview";

const emit = defineEmits(["init"]);
const interviewTitle = ref();

const toggleModal = () => {
  document
    .getElementById("createInterviewModal")
    .classList.toggle("modal-open");
  interviewTitle.value = null;
};

const clickCreateInterviewBtn = async () => {
  if (!interviewTitle.value) {
    alert("직무를 입력해주세요.");
    return;
  }
  const requestData = {
    userId: sessionStorage.getItem("userId"),
    interviewTitle: interviewTitle.value,
  };
  const response = await createInterview(requestData);
  if (response.status >= 200 && response.status < 300) {
    alert("면접 생성이 완료됐습니다.");
    emit("init");
    toggleModal();
  }
};

defineExpose({
  toggleModal,
});
</script>
