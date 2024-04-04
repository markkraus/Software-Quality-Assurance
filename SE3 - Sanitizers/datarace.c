#include <stdio.h>
#include <pthread.h>

int shared = 0;
pthread_mutex_t mutex;

void *add(void *unused) {
  for(int i=0; i < 1000000; i++) { 
    pthread_mutex_lock(&mutex);
    shared++;
    pthread_mutex_unlock(&mutex);
  }
  return NULL;
}

int main() {
  pthread_t t;
  
  pthread_mutex_init(&mutex, NULL);
  // Child thread starts running add
  pthread_create(&t, NULL, add, NULL);
  // Main thread starts running add
  add(NULL);
  // Wait until child thread t terminates
  pthread_join(t, NULL);
  printf("shared=%d\n", shared);
  pthread_mutex_destroy(&mutex);
  return 0;
}
