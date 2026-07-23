<script lang="ts">
  import { page } from '$app/stores';
  import { goto } from '$app/navigation';
  import { addressesApi } from '$lib/api/addresses.js';

  const userId = $derived($page.params.id!);

  let street = $state('');
  let city = $state('');
  let country = $state('');
  let zip = $state('');
  let error = $state('');

  async function submit(e: Event) {
    e.preventDefault();
    error = '';
    try {
      await addressesApi.add(userId, { street, city, country, zip });
      goto(`/users/${userId}`);
    } catch (err) {
      error = (err as Error).message;
    }
  }
</script>

<h2>Add Address</h2>

<form onsubmit={submit}>
  {#if error}<p class="error">{error}</p>{/if}

  <label>Street<input bind:value={street} required /></label>
  <label>City<input bind:value={city} required /></label>
  <label>Country<input bind:value={country} required /></label>
  <label>ZIP<input bind:value={zip} required /></label>

  <div style="display:flex;gap:.5rem;">
    <button type="submit" class="btn">Add</button>
    <a href="/users/{userId}"><button type="button" class="btn-secondary">Cancel</button></a>
  </div>
</form>
