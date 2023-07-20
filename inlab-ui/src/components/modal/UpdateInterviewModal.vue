<template>
  <dialog id="updateInterviewModal" class="modal">
    <form method="dialog" class="modal-box">
      <h2 class="font-bold text-lg mb-6">면접 제목 수정</h2>
      <input
        v-model="interviewTitle"
        type="text"
        name="registerInterview"
        class="input input-bordered w-full mr-2 border-gray-300 text-sm"
        placeholder="수정되는 면접 제목을 입력해주세요."
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
          class="flex flex-col py-5 px-5 btn btn-sm btn-primary"
          type="button"
          @click="clickUpdateInterviewBtn"
        >
          확인
        </button>
      </div>
    </form>
  </dialog>
</template>

<script setup>
import { defineEmits, ref } from "vue";
import { updateInterview } from "@/api/interview";

const emit = defineEmits(["init"]);

const interviewId = ref();
const interviewTitle = ref("");

const toggleModal = (interviewData) => {
  if (interviewData) {
    interviewId.value = interviewData.interviewId;
    interviewTitle.value = interviewData.interviewTitle;
  }
  document
    .getElementById("updateInterviewModal")
    .classList.toggle("modal-open");
};

const clickUpdateInterviewBtn = async () => {
  if (!interviewTitle.value) {
    alert("면접 제목을 입력해주세요.");
    return;
  }
  const requestData = {
    userId: sessionStorage.getItem("userId"),
    interviewTitle: interviewTitle.value,
  };
  let response = await updateInterview(requestData, interviewId.value);
  if (response.status >= 200 && response.status < 300) {
    alert("면접 제목 수정이 완료됐습니다.");
    emit("init");
    toggleModal();
  }
};

defineExpose({
  toggleModal,
});
</script>
