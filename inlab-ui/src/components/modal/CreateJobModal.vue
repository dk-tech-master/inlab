<template>
  <dialog id="createJobModal" class="modal">
    <form method="dialog" class="modal-box">
      <h2 class="font-bold text-lg mb-6">직무 등록</h2>
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
          class="flex flex-col mr-3 py-5 px-5 btn btn-sm btn-primary btn-outline"
          type="button"
          @click="toggleModal"
        >
          취소
        </button>
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
import { ref, defineEmits } from "vue";
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
