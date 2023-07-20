<template>
  <dialog id="verificationAddModal" class="modal">
    <div method="dialog" class="modal-box p-10">
      <h2 class="mb-8 text-xl font-semibold tracking-tight">질문 접근 권한</h2>
      <div
        class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg"
      >
        <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
          <div class="w-[50%] flex flex-col justify-center px-6 py-2 text-left">
            직무
          </div>
          <div class="w-[50%] flex flex-col justify-center px-6 py-2 text-left">
            난이도
          </div>
        </div>
      </div>

      <div
        class="flex border-b hover:bg-gray-100"
        v-for="info in infos"
        :key="info.positionId"
      >
        <div class="w-[50%] flex flex-col justify-center px-6 py-4 text-left">
          {{ info }}
        </div>
        <div class="w-[50%] flex flex-col justify-center px-6 py-4 text-left">
          {{ info }}
        </div>
      </div>

      <button
        class="mt-10 w-full flex flex-col mr-3 py-5 px-7 btn btn-sm btn-primary btn-outline"
        type="button"
        @click="toggleModal"
      >
        확인
      </button>
    </div>
  </dialog>
</template>
<script setup>
import { ref } from "vue";
import { getAccessVerification } from "@/api/interviewer";

const userId = ref(null);
const infos = ref([]);

const init = async (userId) => {
  const axiosResponse = await getAccessVerification(userId);
  console.log(axiosResponse.data.positionIdAndLevelIds);
  infos.value = axiosResponse.data.positionIdAndLevelIds;
  console.log(infos.value);
};

const toggleModal = async (id) => {
  userId.value = id;
  document
    .getElementById("verificationAddModal")
    .classList.toggle("modal-open");
  await init(id);
};

defineExpose({
  toggleModal,
});
</script>
