<template>
  <dialog id="verificationModal" class="modal">
    <div method="dialog" class="modal-box">
      <div class="flex justify-between items-center mb-8">
        <h2 class="font-bold text-xl">질문 접근 권한</h2>
        <label class="btn btn-sm btn-circle btn-ghost" @click="toggleModal"
          >✕</label
        >
      </div>
      <template v-if="infos.length > 0">
        <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
          <div
            class="flex items-center bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2"
          >
            <div class="w-[50%] flex flex-col justify-center py-2 text-left">
              직무
            </div>
            <div class="w-[50%] flex flex-col justify-center py-2 text-left">
              난이도
            </div>
          </div>
        </div>

        <div
          class="flex items-center border-b hover:bg-gray-100 gap-x-4 px-2"
          v-for="info in infos"
          :key="info.positionId"
        >
          <div class="w-[50%] flex flex-col justify-center py-2 text-left">
            {{ info.positionName }}
          </div>
          <div class="w-[50%] flex flex-col justify-center py-2 text-left">
            {{ info.levelName }}
          </div>
        </div>
      </template>
      <template v-else>
        <EmptyState :message="`질문 접근권한이 존재하지 않습니다.`" />
      </template>

      <button
        class="w-full btn btn-primary btn-outline mt-4"
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
import EmptyState from "@/components/common/EmptyState.vue";

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
  document.getElementById("verificationModal").classList.toggle("modal-open");
  await init(id);
};

defineExpose({
  toggleModal,
});
</script>