<script lang="ts">
  import { goto } from '$app/navigation';
  import { usersApi } from '$lib/api/users.js';

  let firstname = $state('');
  let lastname = $state('');
  let email = $state('');
  let photo = $state('');
  let error = $state('');

  async function submit(e: Event) {
    e.preventDefault();
    error = '';
    try {
      const user = await usersApi.create({ firstname, lastname, email, photo: photo || null });
      goto(`/users/${user.id}`);
    } catch (err) {
      error = (err as Error).message;
    }
  }
</script>

<h2>New User</h2>

<form onsubmit={submit}>
  {#if error}<p class="error">{error}</p>{/if}

  <label>First name<input bind:value={firstname} required /></label>
  <label>Last name<input bind:value={lastname} required /></label>
  <label>Email<input type="email" bind:value={email} required /></label>
  <label>Photo URL<input type="url" bind:value={photo} placeholder="https://..." /></label>

  <div style="display:flex;gap:.5rem;">
    <button type="submit" class="btn">Create</button>
    <a href="/"><button type="button" class="btn-secondary">Cancel</button></a>
  </div>
</form>
