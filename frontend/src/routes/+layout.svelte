<script lang="ts">
  let { children } = $props();
</script>

<nav>
  <a href="/">Users</a>
  <a href="/users/new">+ New User</a>
</nav>

<main>
  {@render children()}
</main>

<style>
  :global(*, *::before, *::after) { box-sizing: border-box; margin: 0; padding: 0; }
  :global(body) { font-family: system-ui, sans-serif; font-size: 15px; color: #111; background: #fafafa; }
  :global(a) { color: #2563eb; text-decoration: none; }
  :global(a:hover) { text-decoration: underline; }
  :global(input, select, textarea) {
    width: 100%; padding: 0.4rem 0.6rem; border: 1px solid #d1d5db; border-radius: 4px;
    font-size: 0.95rem; font-family: inherit;
  }
  :global(button) {
    cursor: pointer; border: none; border-radius: 4px; padding: 0.4rem 0.9rem;
    font-size: 0.9rem; font-family: inherit;
  }
  :global(.btn) { background: #2563eb; color: #fff; }
  :global(.btn:hover) { background: #1d4ed8; }
  :global(.btn-danger) { background: #dc2626; color: #fff; }
  :global(.btn-danger:hover) { background: #b91c1c; }
  :global(.btn-secondary) { background: #e5e7eb; color: #111; }
  :global(.btn-secondary:hover) { background: #d1d5db; }
  :global(table) { width: 100%; border-collapse: collapse; }
  :global(th, td) { padding: 0.6rem 0.75rem; text-align: left; border-bottom: 1px solid #e5e7eb; }
  :global(th) { font-weight: 600; background: #f3f4f6; }
  :global(.error) { color: #dc2626; font-size: 0.9rem; margin-top: 0.4rem; }
  :global(form) { display: flex; flex-direction: column; gap: 0.75rem; max-width: 480px; }
  :global(label) { font-size: 0.88rem; font-weight: 500; margin-bottom: 0.2rem; display: block; }

  nav {
    display: flex; align-items: center; gap: 1.5rem;
    padding: 0.75rem 1.5rem; background: #fff; border-bottom: 1px solid #e5e7eb;
  }
  main { padding: 1.5rem; }
</style>
