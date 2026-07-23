<script lang="ts">
  import { onMount } from 'svelte';
  import { page } from '$app/stores';
  import { goto } from '$app/navigation';
  import { usersApi, type User } from '$lib/api/users.js';
  import { addressesApi } from '$lib/api/addresses.js';

  let user = $state<User | null>(null);
  let error = $state('');

  const id = $derived($page.params.id!);

  onMount(async () => {
    try {
      user = await usersApi.get(id);
    } catch (e) {
      error = (e as Error).message;
    }
  });

  async function removeAddress(addressId: string) {
    if (!confirm('Delete this address?')) return;
    try {
      await addressesApi.remove(id, addressId);
      if (user) user.addresses = user.addresses.filter((a) => a.id !== addressId);
    } catch (e) {
      error = (e as Error).message;
    }
  }

  async function removeUser() {
    if (!confirm('Delete this user and all their addresses?')) return;
    try {
      await usersApi.remove(id);
      goto('/');
    } catch (e) {
      error = (e as Error).message;
    }
  }
</script>

{#if error}<p class="error">{error}</p>
{:else if user}
  <div style="display:flex;align-items:center;gap:1rem;margin-bottom:1rem;">
    {#if user.photo}
      <img src={user.photo} alt="avatar" style="width:56px;height:56px;border-radius:50%;object-fit:cover;" />
    {/if}
    <div>
      <h2>{user.firstname} {user.lastname}</h2>
      <p style="color:#6b7280">{user.email}</p>
    </div>
    <div style="margin-left:auto;display:flex;gap:.5rem;">
      <a href="/users/{user.id}/edit"><button class="btn-secondary">Edit</button></a>
      <button class="btn-danger" onclick={removeUser}>Delete user</button>
    </div>
  </div>

  <div style="display:flex;align-items:center;gap:1rem;margin-bottom:.75rem;">
    <h3>Addresses</h3>
    <a href="/users/{user.id}/addresses/new"><button class="btn" style="padding:.3rem .7rem;font-size:.85rem;">+ Add</button></a>
  </div>

  {#if user.addresses.length === 0}
    <p style="color:#6b7280">No addresses yet.</p>
  {:else}
    <table>
      <thead>
        <tr><th>Street</th><th>City</th><th>Country</th><th>ZIP</th><th></th></tr>
      </thead>
      <tbody>
        {#each user.addresses as addr}
          <tr>
            <td>{addr.street}</td>
            <td>{addr.city}</td>
            <td>{addr.country}</td>
            <td>{addr.zip}</td>
            <td style="display:flex;gap:.5rem;">
              <a href="/users/{user.id}/addresses/{addr.id}/edit">
                <button class="btn-secondary">Edit</button>
              </a>
              <button class="btn-danger" onclick={() => removeAddress(addr.id)}>Delete</button>
            </td>
          </tr>
        {/each}
      </tbody>
    </table>
  {/if}
{:else}
  <p>Loading…</p>
{/if}

<p style="margin-top:1.5rem"><a href="/">← Back</a></p>
