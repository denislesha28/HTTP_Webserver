curl -v -X POST -H "Content-Type: application/json" \
    -d '{"userId": 5, "title": "Hello World", "body": "Message 0."}' \
    localhost:1111/messages &&
curl -v -X POST -H "Content-Type: application/json" \
    -d '{"title": "Hello World", "body": "Message 1"}' \
    localhost:1111/messages 
curl -v -X POST -H "Content-Type: application/json" \
    -d '{"title": "Hello World", "body": "Message 2"}' \
    localhost:1111/messages 
curl -v -X PUT -H "Content-Type: application/json" \
    -d '{"title": "Hello World", "body": "Updated Message"}' \
    localhost:1111/messages/1 
curl -v -X DELETE localhost:1111/messages/2 
curl -v GET localhost:1111
    

