const BASE_URL = "http://localhost:8080";

export const getAllTickets = async () => {
  const response = await fetch(`${BASE_URL}/api/v1/tickets`);
  const data = await response.json();
  return data;
};

export const getSortedAndPagedTickets = async (sortBy, order, page, size) => {
  const response = await fetch(
    `${BASE_URL}/api/v1/tickets?sortBy=${sortBy}&direction=${order}&page=${page}&pageSize=${size}`
  );
  const data = await response.json();
  return data;
};

export const getSortedAndFilteredAndPagedTickets = async (
  sortBy,
  order,
  status,
  page,
  size
) => {
  const response = await fetch(
    `${BASE_URL}/api/v1/tickets?sortBy=${sortBy}&direction=${order}&status=${status}&page=${page}&pageSize=${size}`
  );
  const data = await response.json();
  return data;
};

export const updateTicketStatus = async (id, newStatus) => {
  const response = await fetch(`${BASE_URL}/api/v1/tickets/${id}/status`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newStatus),
  });
  const data = await response.json();
  return data;
};

export const updateTicket = async (id, updatedTicket) => {
  const response = await fetch(`${BASE_URL}/api/v1/tickets/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedTicket),
  });
  const data = await response.json();
  return data;
};

export const createTicket = async (newTicket) => {
  const response = await fetch(`${BASE_URL}/api/v1/tickets`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newTicket),
  });
  const data = await response.json();
  return data;
};
