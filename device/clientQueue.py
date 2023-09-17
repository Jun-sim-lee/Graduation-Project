import numpy as np

class clientQueue:
    def __init__(self, qsize):
        self.queue_size = qsize
        self.queue = []
    
    def push_value(self, value):
        if len(self.queue) < self.queue_size:
            self.queue.append(value) # 자리가 남아있으면 Push
        else:
            self.queue = self._queue[1:] # 자리가 없으면 앞에서 부터 pop
            self.queue.append(value)
        
        return self.get_median()
        
    def get_median(self):
        if len(self.queue) > 0:
            return np.median(self.queue)
        else:
            return 0