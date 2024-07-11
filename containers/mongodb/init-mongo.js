db.createUser({
  user: "admin",
  pwd: "admin",
  roles: [{ role: "root", db: "admin" }]
});

db.createUser({
  user: "user",
  pwd: "user",
  roles: [{ role: "readWrite", db: "booksdb" }]
});
