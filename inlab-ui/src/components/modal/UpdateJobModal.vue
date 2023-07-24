<template>
  <dialog id="updateJobModal" class="modal">
    <form method="dialog" class="modal-box">
      <div class="flex justify-between items-center mb-8">
        <h2 class="font-bold text-xl">직무 수정</h2>
        <label class="btn btn-sm btn-circle btn-ghost" @click="toggleModal"
          >✕</label
        >
      </div>
      <input
        v-model="jobName"
        type="text"
        name="registerJob"
        class="input input-bordered w-full mr-2 border-gray-300 text-sm"
        placeholder="수정할 직무를 입력해주세요."
        required
      />
      <div class="modal-action">
        <button
          class="flex flex-col py-5 px-5 btn btn-sm btn-primary"
          type="button"
          @click="clickUpdateJobBtn"
        >
          확인
        </button>
      </div>
    </form>
  </dialog>
</template>

<script setup>
import { defineEmits, ref } from "vue";
import { updateJobs } from "@/api/job";

const emit = defineEmits(["init"]);

const jobId = ref();
const jobName = ref();

const toggleModal = (jobData) => {
  if (jobData) {
    jobId.value = jobData.positionId;
    jobName.value = jobData.positionName;
  }
  document.getElementById("updateJobModal").classList.toggle("modal-open");
};

const clickUpdateJobBtn = async () => {
  if (!jobName.value) {
    alert("직무를 입력해주세요.");
    return;
  }
  const requestData = {
    positionName: jobName.value,
  };
  await updateJobs(requestData, jobId.value);
  alert("직무 수정이 완료됐습니다.");
  emit("init");
  toggleModal();
};

defineExpose({
  toggleModal,
});
</script>
