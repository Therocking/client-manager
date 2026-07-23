<script lang="ts">
  import { onMount } from 'svelte';
  import { page } from '$app/stores';
  import { goto } from '$app/navigation';
  import { usersApi } from '$lib/api/users.js';

  const id = $derived($page.params.id!);

  let firstname = $state('');
  let lastname = $state('');
  let email = $state('');
  let photo = $state('');
  let error = $state('');

  onMount(async () => {
    try {
      const user = await usersApi.get(id);
      firstname = user.firstname;
      lastname = user.lastname;
      email = user.email;
      photo = user.photo ?? '';
    } catch (e) {
      error = (e as Error).message;
    }
  });

  async function submit(e: Event) {
    e.preventDefault();
    error = '';
    try {
      await usersApi.update(id, { firstname, lastname, email, photo: photo || null });
      goto(`/users/${id}`);
    } catch (err) {
      error = (err as Error).message;
    }
  }
</script>

<h2>Edit User</h2>

<form onsubmit={submit}>
  {#if error}<p class="error">{error}</p>{/if}

  <label>First name<input bind:value={firstname} required /></label>
  <label>Last name<input bind:value={lastname} required /></label>
  <label>Email<input type="email" bind:value={email} required /></label>
  <label>Photo URL<input type="url" bind:value={photo} placeholder="https://..." /></label>

  <div style="display:flex;gap:.5rem;">
    <button type="submit" class="btn">Save</button>
    <a href="/users/{id}"><button type="button" class="btn-secondary">Cancel</button></a>
  </div>
</form>
