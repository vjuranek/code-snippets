#!/usr/bin/env python

import os

import psycopg2
import requests

model_id = "sentence-transformers/all-MiniLM-L6-v2"
hf_token = os.environ.get("HF_API_KEY")

api_url = f"https://api-inference.huggingface.co/pipeline/feature-extraction/{model_id}"
headers = {"Authorization": f"Bearer {hf_token}"}

def embeddings(texts):
    response = requests.post(api_url, headers=headers, json={"inputs": texts, "options":{"wait_for_model":True}})
    return response.json()


conn = psycopg2.connect(database="postgres", user="postgres", password="password", host="172.17.0.2", port="5432")

cur = conn.cursor()
cur.execute("SELECT id, content FROM documents")
documents = cur.fetchall()
texts = [d[1] for d in documents]
print(texts)

text_embeddings = embeddings(texts)
for i in range(len(documents)):
    print(f"{documents[i][0]} -> {text_embeddings[i]}\n")
    cur.execute(f"INSERT INTO document_embeddings (id, embedding) VALUES ({documents[i][0]}, '{text_embeddings[i]}');")
    conn.commit()

