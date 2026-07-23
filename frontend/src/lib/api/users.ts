const BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

export interface Address {
  id: string;
  street: string;
  city: string;
  country: string;
  zip: string;
  userId: string;
}

export interface User {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  photo: string | null;
  addresses: Address[];
}

export interface UserPayload {
  firstname: string;
  lastname: string;
  email: string;
  photo?: string | null;
}

async function json<T>(res: Response): Promise<T> {
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error((err as { message?: string }).message ?? `HTTP ${res.status}`);
  }
  if (res.status === 204) return undefined as T;
  return res.json();
}

export const usersApi = {
  list: (): Promise<User[]> =>
    fetch(`${BASE}/api/v1/users`).then(json<User[]>),

  get: (id: string): Promise<User> =>
    fetch(`${BASE}/api/v1/users/${id}`).then(json<User>),

  create: (payload: UserPayload): Promise<User> =>
    fetch(`${BASE}/api/v1/users`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    }).then(json<User>),

  update: (id: string, payload: UserPayload): Promise<User> =>
    fetch(`${BASE}/api/v1/users/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    }).then(json<User>),

  remove: (id: string): Promise<void> =>
    fetch(`${BASE}/api/v1/users/${id}`, { method: 'DELETE' }).then(json<void>),
};
