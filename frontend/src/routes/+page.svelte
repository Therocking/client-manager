<script lang="ts">
  import { onMount } from 'svelte';
  import { usersApi, type User } from '$lib/api/users.js';

  let users = $state<User[]>([]);
  let error = $state('');

  onMount(async () => {
    try {
      users = await usersApi.list();
    } catch (e) {
      error = (e as Error).message;
    }
  });

  async function remove(id: string) {
    if (!confirm('Delete this user?')) return;
    try {
      await usersApi.remove(id);
      users = users.filter((u) => u.id !== id);
    } catch (e) {
      error = (e as Error).message;
    }
  }
</script>

<h2>Users</h2>

{#if error}<p class="error">{error}</p>{/if}

{#if users.length === 0 && !error}
  <p style="color:#6b7280;margin-top:1rem">No users yet. <a href="/users/new">Create one</a>.</p>
{:else}
  <table>
    <thead>
      <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Addresses</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      {#each users as user}
        <tr>
          <td><a href="/users/{user.id}">{user.firstname} {user.lastname}</a></td>
          <td>{user.email}</td>
          <td>{user.addresses.length}</td>
          <td style="display:flex;gap:.5rem;">
            <a href="/users/{user.id}/edit"><button class="btn-secondary">Edit</button></a>
            <button class="btn-danger" onclick={() => remove(user.id)}>Delete</button>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
{/if}
