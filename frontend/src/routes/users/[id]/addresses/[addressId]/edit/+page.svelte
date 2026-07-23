<script lang="ts">
  import { onMount } from 'svelte';
  import { page } from '$app/stores';
  import { goto } from '$app/navigation';
  import { usersApi } from '$lib/api/users.js';
  import { addressesApi } from '$lib/api/addresses.js';

  const userId = $derived($page.params.id!);
  const addressId = $derived($page.params.addressId!);

  let street = $state('');
  let city = $state('');
  let country = $state('');
  let zip = $state('');
  let error = $state('');

  onMount(async () => {
    try {
      const user = await usersApi.get(userId);
      const addr = user.addresses.find((a) => a.id === addressId);
      if (!addr) throw new Error('Address not found');
      street = addr.street;
      city = addr.city;
      country = addr.country;
      zip = addr.zip;
    } catch (e) {
      error = (e as Error).message;
    }
  });

  async function submit(e: Event) {
    e.preventDefault();
    error = '';
    try {
      await addressesApi.update(userId, addressId, { street, city, country, zip });
      goto(`/users/${userId}`);
    } catch (err) {
      error = (err as Error).message;
    }
  }
</script>

<h2>Edit Address</h2>

<form onsubmit={submit}>
  {#if error}<p class="error">{error}</p>{/if}

  <label>Street<input bind:value={street} required /></label>
  <label>City<input bind:value={city} required /></label>
  <label>Country<input bind:value={country} required /></label>
  <label>ZIP<input bind:value={zip} required /></label>

  <div style="display:flex;gap:.5rem;">
    <button type="submit" class="btn">Save</button>
    <a href="/users/{userId}"><button type="button" class="btn-secondary">Cancel</button></a>
  </div>
</form>
