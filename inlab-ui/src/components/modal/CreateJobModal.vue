<template>
  <dialog id="createJobModal" class="modal">
    <form method="dialog" class="modal-box">
      <div class="flex justify-between items-center mb-8">
        <h2 class="font-bold text-xl">직무 등록</h2>
        <label class="btn btn-sm btn-circle btn-ghost" @click="toggleModal"
          >✕</label
        >
      </div>
      <input
        v-model="jobName"
        type="text"
        name="registerJob"
        class="input input-bordered w-full mr-2 border-gray-300 text-sm"
        placeholder="추가할 직무를 입력해주세요."
        required
      />
      <div class="modal-action">
        <button
          type="button"
          class="flex flex-col py-5 px-5 btn btn-sm btn-primary"
          @click="clickCreateJobBtn"
        >
          확인
        </button>
      </div>
    </form>
  </dialog>
</template>

<script setup>
import { defineEmits, ref } from "vue";
import { createJobs } from "@/api/job";

const emit = defineEmits(["init"]);
const jobName = ref();

const toggleModal = () => {
  document.getElementById("createJobModal").classList.toggle("modal-open");
  jobName.value = null;
};

const clickCreateJobBtn = async () => {
  if (!jobName.value) {
    alert("직무를 입력해주세요.");
    return;
  }
  const requestData = {
    positionName: jobName.value,
  };
  console.log(requestData);
  await createJobs(requestData);
  alert("직무 생성이 완료됐습니다.");
  emit("init");
  toggleModal();
};

defineExpose({
  toggleModal,
});
</script>
