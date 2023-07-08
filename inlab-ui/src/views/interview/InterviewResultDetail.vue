<template>
  <header class="ml-80 mt-8">
    <div>
      <div class="mb-10">
        <p class="mb-1 text-sm font-light text-gray-500">
          면접 관리 >
          <span class="font-medium text-indigo-500">면접 결과 관리</span>
        </p>
        <h2 class="text-3xl tracking-tight font-bold text-gray-800">
          면접 결과 관리
        </h2>
      </div>
      <div class="mb-10 py-7 px-5 shadow">
        <h4 class="text-base mb-1 tracking-tight">스프링 개발자 면접</h4>
        <h2 class="text-2xl font-medium">
          <span class="text-primary font-bold">신다인</span>님의 면접 결과
        </h2>
      </div>
    </div>
  </header>

  <section class="w-3/4 mx-auto ml-80">
    <div class="flex justify-between items-end border-b-2">
      <span class="mb-2 text-base font-semibold text-gray-800">질문01</span>
      <div class="flex mb-2">
        <button
          class="flex flex-col mr-3 px-7 py-5 btn btn-outline btn-primary btn-sm"
        >
          수정
        </button>
        <button
          class="flex flex-col items-center mr-2 px-7 py-5 btn btn-primary btn-sm"
        >
          AI 평가 측정
        </button>
      </div>
    </div>
    <h3 class="text-gray-800 my-5 font-semibold">
      스프링 프레임워크의 핵심 개념과 이점에 대해 설명해주세요.
    </h3>
    <div
      v-for="index in 4"
      :key="index"
      class="flex items-center justify-start mb-3"
    >
      <input
        type="checkbox"
        checked="checked"
        class="rounded focus:ring-2 focus:ring-primary checkbox checkbox-sm checkbox-primary"
      />
      <p for="default-checkbox" class="ml-2 text-sm font-medium text-gray-900">
        해당 질문을 제대로 대답했는가?
      </p>
    </div>
    <div class="my-8">
      <label class="label">
        <span class="label-text">코멘트</span>
      </label>
      <textarea
        class="w-full bg-gray-100 textarea textarea-bordered h-24"
        placeholder="면접관님의 의견을 적어주세요"
      ></textarea>
    </div>
    <div class="my-8">
      <label class="label">
        <span class="label-text">응답기록</span>
      </label>
      <textarea
        class="w-full bg-gray-100 textarea textarea-bordered h-24"
      ></textarea>
    </div>
    <div class="my-8">
      <label class="label">
        <span class="label-text">AI평가</span>
      </label>
      <textarea
        class="w-full bg-gray-100 textarea textarea-bordered h-24"
      ></textarea>
    </div>
  </section>
</template>

<script setup></script>
