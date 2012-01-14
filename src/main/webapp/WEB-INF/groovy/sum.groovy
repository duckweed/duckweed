import org.duckweedcoll.Sum

def sum = new Sum();

def get = {
    if(params.get(it) == null){
        return 0
    }
    params.get(it) as Integer
}

sum.setA(get('a'))
sum.setB(get('b'))

request.setAttribute 'sum', sum.sum()

forward '/sum.gtpl'